package com.sclab.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionResponseModel {
    private String id;
    private Date transactionDate;
    private Date bookDueDate;
    private boolean isIssued;
    private boolean isReturned;
    private double fineAmount;
    private String status;
    private Date createdOn;
    private Date updatedOn;
}