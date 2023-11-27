package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.entity.Card;
import com.example.demo.entity.Transaction;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/transaction/{id}")
    public Transaction getSingle(@PathVariable String id){
        Transaction transaction = transactionService.getSingle(id);
        transaction.setCard(null);
        transaction.setBook(null);
        return transaction;
    }

    @GetMapping("/transaction")
    public List<Transaction> getAll(){
        List<Transaction> transactionList = transactionService.getAll();
        for (Transaction transaction : transactionList) {
            transaction.setBook(null);
            transaction.setCard(null);
        }
        return transactionList;
    }

    @GetMapping("/transaction/{transactionID}/book")
    public Book getBookUsingTransactionId(@PathVariable String transactionID){
        Transaction transaction = transactionService.getSingle(transactionID);
        return transaction.getBook();
    }

    @GetMapping("/transaction/{transactionID}/card")
    public Card getCardUsingTransactionId(@PathVariable String transactionID){
        Transaction transaction = transactionService.getSingle(transactionID);
        return transaction.getCard();
    }

}
