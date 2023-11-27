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
        Optional<Transaction> transaction = transactionRepository.findById(id);
        return transaction.get();
    }

}