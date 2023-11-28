package com.sclab.library.service;

import com.sclab.library.entity.Book;
import com.sclab.library.entity.Card;
import com.sclab.library.entity.Transaction;
import com.sclab.library.enumeration.TransactionStatus;
import com.sclab.library.repository.BookRepository;
import com.sclab.library.repository.CardRepository;
import com.sclab.library.repository.TransactionRepository;
import com.sclab.library.util.CustomMessage;
import com.sclab.library.util.CustomResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private BookRepository bookRepository;

    private final String ACTIVE = "Active";
    private final int MAX_ISSUE_ALLOWED = 3;

    public ResponseEntity issueBook(String cardId, String bookId) {
        Optional<Card> optCard = cardRepository.findById(cardId);
        Optional<Book> optBook = bookRepository.findById(bookId);
        Transaction savedData = null;
        if (optCard.isPresent() && optBook.isPresent()) {
            Card card = optCard.get();
            Book book = optBook.get();
            Date currentDate = new Date(System.currentTimeMillis());
            boolean isCardActive = card.getStatus().equalsIgnoreCase(ACTIVE);
            boolean isBookAvailable = book.isAvailable();
            int countOfIssue = card.getTotalIssuedBook();
            if (isBookAvailable && isCardActive && countOfIssue < MAX_ISSUE_ALLOWED) {
                Transaction transaction = new Transaction();
                transaction.setCard(optCard.get());
                transaction.setBook(optBook.get());
                transaction.setTransactionDate(currentDate);
                transaction.setCreatedOn(currentDate);
                transaction.setUpdatedOn(currentDate);
                transaction.setBookDueDate(calculateDueDate());
                transaction.setIssued(true);
                transaction.setStatus(TransactionStatus.ISSUED);
                savedData = transactionRepository.save(transaction);
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
        if (savedData == null) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new CustomMessage("transaction not saved", 501)
            );
        }
        savedData.setBook(null);
        savedData.setCard(null);
        return ResponseEntity.status(HttpStatus.OK).body(savedData);
    }

    private Date calculateDueDate() {
        Date date = new Date(System.currentTimeMillis());
        return Date.valueOf(date.toLocalDate().plusDays(14));
    }


    public List<Transaction> getAll() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions;
    }

    public Transaction getSingle(String id) {
        Optional<Transaction> optTransaction = transactionRepository.findById(id);
//        if (optTransaction.isPresent()) {
//            Transaction transaction = optTransaction.get();
//            // Load associated Book and Card entities
////            String dd = transaction.getBook().getId(); // This will trigger a lazy load for Book
////            String oo= transaction.getCard().getId(); // This will trigger a lazy load for Card
//        }
        return optTransaction.get();
    }

//    public Optional<Transaction> getTransactionWithDetails(String transactionId) {
//        Optional<Transaction> transactionOptional = transactionRepository.findById(transactionId);
//
//        if (transactionOptional.isPresent()) {
//            Transaction transaction = transactionOptional.get();
//            // Load associated Book and Card entities
//            String dd = transaction.getBook().getId(); // This will trigger a lazy load for Book
//            String oo= transaction.getCard().getId(); // This will trigger a lazy load for Card
//        }
//
//        return transactionOptional;
//    }

}