package com.sclab.library.service;

import com.sclab.library.entity.Transaction;
import com.sclab.library.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

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