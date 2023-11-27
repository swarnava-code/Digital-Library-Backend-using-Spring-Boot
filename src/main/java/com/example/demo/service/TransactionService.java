package com.example.demo.service;

import com.example.demo.entity.Author;
import com.example.demo.entity.Transaction;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.TransactionRepository;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    Configuration cfg;
    SessionFactory sessionFactory;
    @Autowired
    TransactionRepository transactionRepository;

//    public TransactionService() {
//        cfg = new Configuration();
//        sessionFactory = cfg.configure("hbm.cfg.xml").buildSessionFactory();
//    }

    public List<Transaction> getAll() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions;
    }

    public Transaction getSingle(String id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        return transaction.get();
    }

}