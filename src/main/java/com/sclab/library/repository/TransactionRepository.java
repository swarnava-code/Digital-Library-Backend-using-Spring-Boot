package com.sclab.library.repository;

import com.sclab.library.entity.Transaction;
import com.sclab.library.enumeration.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    public List<Transaction> findByBookId(String bookId);

    public List<Transaction> findByCardId(String cardId);

    public List<Transaction> findByCardIdAndBookId(String cardId, String bookId);

    public List<Transaction> findByCardIdAndBookIdAndStatusEquals(String cardId, String bookId,
                                                                  TransactionStatus status);

    public List<Transaction> findByTransactionDateBetween(Date startDate, Date endDate);

    public List<Transaction> findByTransactionDateBetweenAndStatusEquals(Date startDate, Date endDate,
                                                                         TransactionStatus status);

    public List<Transaction> findByUpdatedOnBetweenAndStatusEquals(Date startDate, Date endDate,
                                                                   TransactionStatus status);

    @Query(value = "SELECT DISTINCT t.card_id FROM Transaction t " +
            "WHERE DATE(t.transaction_date) = DATE(:transactionDate) " +
            "AND t.status <> 'FAILURE'", nativeQuery = true)
    Set<String> findDistinctCardIdByTransactionDate(@Param("transactionDate") Date transactionDate);

}