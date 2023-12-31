package com.sclab.library.service;

import com.sclab.library.dto.TransactionDTO;
import com.sclab.library.entity.Author;
import com.sclab.library.entity.Book;
import com.sclab.library.entity.Card;
import com.sclab.library.entity.Transaction;
import com.sclab.library.enumeration.CardStatus;
import com.sclab.library.enumeration.TransactionStatus;
import com.sclab.library.repository.BookRepository;
import com.sclab.library.repository.CardRepository;
import com.sclab.library.repository.TransactionRepository;
import com.sclab.library.util.CustomMessage;
import com.sclab.library.util.CustomResponseEntity;
import com.sclab.library.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Value("${transact.max_issue_allowed}")
    private int maxIssueAllowed;

    @Value("${transact.total_borrow_days}")
    private int totalBorrowDays;

    public ResponseEntity issueBook(String cardId, String bookId) {
        Optional<Card> optCard = cardRepository.findById(cardId);
        Optional<Book> optBook = bookRepository.findById(bookId);
        Card card = null;
        Book book = null;
        Transaction savedTransaction = null;
        if (optCard.isPresent() && optBook.isPresent()) {
            card = optCard.get();
            book = optBook.get();
            Date currentDate = new Date(System.currentTimeMillis());
            boolean isCardActive = card.getStatus().equals(CardStatus.ACTIVE);
            boolean isBookAvailable = book.isAvailable();
            int countOfIssue = card.getTotalIssuedBook();
            if (isBookAvailable && isCardActive && countOfIssue < maxIssueAllowed) {
                Transaction transaction = new Transaction();
                transaction.setCard(optCard.get());
                transaction.setBook(optBook.get());
                transaction.setTransactionDate(currentDate);
                transaction.setCreatedOn(currentDate);
                transaction.setUpdatedOn(currentDate);
                transaction.setBookDueDate(TimeUtil.addDayInDate(totalBorrowDays));//calculateDueDate()
                transaction.setIssued(true);
                transaction.setStatus(TransactionStatus.ISSUED);
                savedTransaction = transactionRepository.save(transaction);
                book.setAvailable(false);
                bookRepository.save(book);
                card.setTotalIssuedBook(++countOfIssue);
                cardRepository.save(card);
            } else {
                Transaction transaction = new Transaction();
                transaction.setTransactionDate(currentDate);
                transaction.setIssued(false);
                transaction.setStatus(TransactionStatus.FAILURE);
                var failedTransaction = transactionRepository.save(transaction);
                return CustomResponseEntity.CUSTOM_MSG(
                        CustomResponseEntity.BAD_REQUEST,
                        "isBookAvailable", isBookAvailable,
                        "isCardActive", isCardActive,
                        "countOfIssue", countOfIssue,
                        "transaction", failedTransaction
                );
            }
        }
        if (savedTransaction == null) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new CustomMessage("transaction not saved", 501)
            );
        }
        TransactionDTO dto = TransactionDTO.fromTransaction(savedTransaction);
        kafkaProducerService.sendBookIssuedNotification(cardId, book.getName(), getAuthorsName(book), dto.getBookDueDate().toString());
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    private String getAuthorsName(Book book) {
        StringBuilder authors = new StringBuilder();
        for (Author author : book.getAuthors()) {
            authors.append(author.getName() + ", ");
        }
        return authors.toString();
    }

    public ResponseEntity getAllTransactionByBookId(String bookId) {
        var transactions = transactionRepository.findByBookId(bookId);
        return CustomResponseEntity.CUSTOM_MSG(200, transactions);
    }

    public ResponseEntity getAllTransactionByCardId(String cardId) {
        var transactions = transactionRepository.findByCardId(cardId);
        return CustomResponseEntity.CUSTOM_MSG(200, transactions);
    }

    @Transactional
    public ResponseEntity returnBook(String cardId, String bookId) {
        var optBook = bookRepository.findById(bookId);
        var optCard = cardRepository.findById(cardId);
        if (optCard.isPresent() && optBook.isPresent()) {
            Book book = optBook.get();
            Card card = optCard.get();
            List<Transaction> transactions = transactionRepository.findByCardIdAndBookIdAndStatusEquals(cardId, bookId, TransactionStatus.ISSUED);
            Transaction transaction = transactions.get(0);
            boolean isCardActive = card.getStatus().equals(CardStatus.ACTIVE);
            boolean isBookAvailable = book.isAvailable();
            int totalIssuedBooks = card.getTotalIssuedBook();
            boolean isBookReturned = transaction.isReturned();
            boolean isBookIssued = transaction.isIssued();
            TransactionStatus transactionStatus = transaction.getStatus();
            boolean isAllCondSatisfied = isCardActive && !isBookAvailable &&
                    totalIssuedBooks > 0 && totalIssuedBooks <= 3 &&
                    !isBookReturned && isBookIssued &&
                    transactionStatus.equals(TransactionStatus.ISSUED);
            if (isAllCondSatisfied) {
                card.setTotalIssuedBook(card.getTotalIssuedBook() - 1);
                book.setAvailable(true);
                transaction.setReturned(true);
                transaction.setIssued(false);
                transaction.setStatus(TransactionStatus.RETURNED);
                // transaction.setCard(null); // we should hava card data since if any student tear the pages of the book
                int fineAmount = calculateFine(transaction.getBookDueDate());
                transaction.setFineAmount(transaction.getFineAmount() + fineAmount);
                // With @Transactional, don't need to call save()
                kafkaProducerService.sendBookReturnedNotification(cardId, book.getName(), getAuthorsName(book),
                        transaction.getCreatedOn().toString(),
                        transaction.getBookDueDate().toString(),
                        transaction.getFineAmount()
                );
                return CustomResponseEntity.CUSTOM_MSG_OK(200,
                        "fineAmount", fineAmount,
                        "book", book,
                        "card", card,
                        "transaction", transaction
                );
            }
            return CustomResponseEntity.CUSTOM_MSG_ERR(HttpStatus.BAD_REQUEST,
                    "isBookAvailable", isBookAvailable,
                    "totalIssuedBooks", totalIssuedBooks,
                    "isBookReturned", isBookReturned,
                    "isBookIssued", isBookIssued,
                    "transactionStatus", transactionStatus
            );
        }
        return CustomResponseEntity.CUSTOM_MSG_ERR(HttpStatus.NOT_FOUND,
                "isBookPresent", optBook.isPresent(),
                "isCardPresent", optCard.isPresent()
        );
    }

    public ResponseEntity calculateFine(String cardId, String bookId) {
        List<Transaction> transactions = transactionRepository.findByCardIdAndBookIdAndStatusEquals(cardId, bookId, TransactionStatus.ISSUED);
        if (transactions.size() <= 0) {
            return CustomResponseEntity.CUSTOM_MSG_ERR(HttpStatus.NOT_FOUND,
                    "message", "it is returned"
            );
        }
        Transaction transaction = transactions.get(0);
        double fineAmount = transaction.getFineAmount();
        boolean isReturned = transaction.isReturned();
        if (!isReturned) {
            fineAmount = calculateFine(transaction.getBookDueDate());
        }
        return CustomResponseEntity.CUSTOM_MSG_OK(200,
                "isReturned", isReturned,
                "fineAmount", fineAmount
        );
    }

    /**
     * Once student cross due date , they have to pay ₹1/- per day
     *
     * @param dueDate
     * @return
     */
    private int calculateFine(Date dueDate) {
        Date currentDate = TimeUtil.currentDate();
        int fine = 0;
        boolean isFineApplicable = dueDate.before(currentDate);
        if (isFineApplicable) {
            Long days = TimeUtil.differenceInDays(dueDate, currentDate);
            fine = days.intValue();
        }
        return fine;
    }

    public List<Transaction> getAll() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions;
    }

    public Transaction getSingle(String id) {
        Optional<Transaction> optTransaction = transactionRepository.findById(id);
        return optTransaction.get();
    }

    public List<Transaction> getBooksBetweenDates(Date startDate, Date endDate) {
        return transactionRepository.findByTransactionDateBetween(startDate, endDate);
    }

    public List<Transaction> getBooksBetweenDatesWithStatus(Date startDate, Date endDate, TransactionStatus status) {
        return transactionRepository.findByTransactionDateBetweenAndStatusEquals(startDate, endDate, status);
    }

    public double getTotalCollectedFine(Date startDate, Date endDate) {
        List<Transaction> transactions = transactionRepository.findByUpdatedOnBetweenAndStatusEquals(startDate, endDate,
                TransactionStatus.RETURNED);
        double sum = 0;
        for (Transaction transaction : transactions) {
            if (transaction.getStatus().equals(TransactionStatus.RETURNED)) {
                sum += transaction.getFineAmount();
            }
        }
        return sum;
    }

    public Set<String> getTotalStudentsSignedUps(Date date) {
        return transactionRepository.findDistinctCardIdByTransactionDate(date);
    }

    public Set<Transaction> findByBookDueDate(Date date) {
        return transactionRepository.findByBookDueDate(date);
    }

}