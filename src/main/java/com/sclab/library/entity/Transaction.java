package com.sclab.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import java.sql.Date;

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
    private String status;
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