package com.sclab.library.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sclab.library.enumeration.TransactionStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    @ManyToOne
    @JoinColumn(name = "card_id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Card card;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Book book;
}