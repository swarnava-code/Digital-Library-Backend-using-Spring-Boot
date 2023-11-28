package com.sclab.library.dto;

import com.sclab.library.entity.Transaction;
import com.sclab.library.enumeration.TransactionStatus;
import lombok.Data;
import java.sql.Date;

@Data
public class TransactionDTO {
    private String id;
    private Date transactionDate;
    private Date bookDueDate;
    private double fineAmount;
    private TransactionStatus status;
    private Date createdOn;
    private Date updatedOn;
    private boolean issued;
    private boolean returned;
    private String cardId;
    private String bookId;

    public static TransactionDTO fromTransaction(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setBookDueDate(transaction.getBookDueDate());
        dto.setFineAmount(transaction.getFineAmount());
        dto.setStatus(transaction.getStatus());
        dto.setCreatedOn(transaction.getCreatedOn());
        dto.setUpdatedOn(transaction.getUpdatedOn());
        dto.setIssued(transaction.isIssued());
        dto.setReturned(transaction.isReturned());
        dto.setCardId(transaction.getCard() != null ? transaction.getCard().getId() : null);
        dto.setBookId(transaction.getBook() != null ? transaction.getBook().getId() : null);
        return dto;
    }
}
