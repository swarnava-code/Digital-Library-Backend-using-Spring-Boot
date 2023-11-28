package com.sclab.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sclab.library.enumeration.TransactionStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import java.sql.Date;
import java.util.UUID;

@Entity
@Table
@Data
public class Transaction {
    @Id
    @UuidGenerator
    private String id;

    private Date transactionDate;
    private Date bookDueDate;
    private boolean isIssued;
    private boolean isReturned;
    private double fineAmount;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    private Date createdOn;
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "card_id")
    @JsonIgnore
    private Card card;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonIgnore
    private Book book;
}