package com.sclab.library.service;

import com.sclab.library.entity.Book;
import com.sclab.library.entity.Card;
import com.sclab.library.entity.Transaction;
import com.sclab.library.util.CustomMessage;
import com.sclab.library.util.CustomResponseEntity;
import com.sclab.library.repository.BookRepository;
import com.sclab.library.repository.CardRepository;
import com.sclab.library.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.Optional;

@Service
public class IssueService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private BookRepository bookRepository;
    private final String ACTIVE = "Active";
    private final int MAX_ISSUE_ALLOWED = 3;

    public ResponseEntity issueBook(String cardId, String bookId) {
        // Perform validation, business logic, and create a new transaction
        // For simplicity, assume that the cardId and bookId are valid
        // You may need to check if the book is available, the card is valid, etc.

        Optional<Card> optCard = cardRepository.findById(cardId);
        Optional<Book> optBook = bookRepository.findById(bookId);
        Transaction savedData = null;
        if (optCard.isPresent() && optBook.isPresent()) {
            Card card = optCard.get();
            Book book = optBook.get();
            boolean isCardActive = card.getStatus().equalsIgnoreCase(ACTIVE);
            boolean isBookAvailable = book.isAvailable();
            int countOfIssue = card.getTotalIssuedBook();
            if (isBookAvailable && isCardActive && countOfIssue < MAX_ISSUE_ALLOWED) {
                Date currentDate = new Date(System.currentTimeMillis());
                Transaction transaction = new Transaction();
                transaction.setCard(optCard.get());
                transaction.setBook(optBook.get());
                transaction.setTransactionDate(currentDate);
                transaction.setCreatedOn(currentDate);
                transaction.setUpdatedOn(currentDate);
                transaction.setBookDueDate(calculateDueDate());
                transaction.setIssued(true);
                transaction.setStatus("ISSUED");
                savedData = transactionRepository.save(transaction);
                book.setAvailable(false);
                bookRepository.save(book);
                card.setTotalIssuedBook(++countOfIssue);
                cardRepository.save(card);
            } else {
                return CustomResponseEntity.CUSTOM_MSG(
                        CustomResponseEntity.BAD_REQUEST,
                        "isBookAvailable", isBookAvailable,
                        "isCardActive", isCardActive,
                        "countOfIssue", countOfIssue
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

}