package com.sclab.library.controller;

import com.sclab.library.entity.Book;
import com.sclab.library.entity.Card;
import com.sclab.library.entity.Transaction;
import com.sclab.library.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/transact")
    public ResponseEntity issueBook(@RequestParam String cardId,
                                    @RequestParam String bookId) {
        // Call the service to issue the book
        ResponseEntity responseEntity = transactionService.issueBook(cardId, bookId);
        return responseEntity;
    }

    @PatchMapping("/transact/")
    public ResponseEntity returnBook(@RequestParam String cardId,
                                     @RequestParam String bookId) {
        return transactionService.returnBook(cardId, bookId);
    }

    @GetMapping("/transact/{id}")
    public Transaction getSingle(@PathVariable String id) {
        Transaction transaction = transactionService.getSingle(id);
        transaction.setCard(null);
        transaction.setBook(null);
        return transaction;
    }

    @GetMapping("/transact")
    public List<Transaction> getAll() {
        List<Transaction> transactionList = transactionService.getAll();
        for (Transaction transaction : transactionList) {
            transaction.setBook(null);
            transaction.setCard(null);
        }
        return transactionList;
    }

    @GetMapping("/transact/{transactionID}/book")
    public Book getBookUsingTransactionId(@PathVariable String transactionID) {
        Transaction transaction = transactionService.getSingle(transactionID);
        return transaction.getBook();
    }

    @GetMapping("/transact/{transactionID}/card")
    public Card getCardUsingTransactionId(@PathVariable String transactionID) {
        Transaction transaction = transactionService.getSingle(transactionID);
        return transaction.getCard();
    }

    @GetMapping("/transact/book/{bookId}")
    public ResponseEntity getAllTransactionByBookId(@PathVariable String bookId) {
        var transaction = transactionService.getAllTransactionByBookId(bookId);
        return transaction;
    }

    @GetMapping("/transact/card/{cardId}")
    public ResponseEntity getAllTransactionByCardId(@PathVariable String cardId) {
        var transaction = transactionService.getAllTransactionByCardId(cardId);
        return transaction;
    }

}