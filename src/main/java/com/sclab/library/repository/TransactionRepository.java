package com.sclab.library.repository;

import com.sclab.library.entity.Transaction;
import com.sclab.library.enumeration.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    public List<Transaction> findByBookId(String bookId);
    public List<Transaction> findByCardId(String cardId);
    public List<Transaction> findByCardIdAndBookId(String cardId, String bookId);
    public List<Transaction> findByCardIdAndBookIdAndStatusEquals(String cardId, String bookId, TransactionStatus status);
}