package com.sclab.library.controller;

import com.sclab.library.entity.Transaction;
import com.sclab.library.enumeration.TransactionStatus;
import com.sclab.library.service.TransactionService;
import com.sclab.library.util.CustomResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
public class ReportController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/report/transaction")
    public ResponseEntity getBooksIssuedBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) TransactionStatus status
    ) {
        Date startSqlDate = Date.valueOf(startDate);
        Date endSqlDate = Date.valueOf(endDate);
        List<Transaction> transactions;
        if (status != null) {
            transactions = transactionService.getBooksBetweenDatesWithStatus(startSqlDate, endSqlDate, status);
        } else {
            transactions = transactionService.getBooksBetweenDates(startSqlDate, endSqlDate);
        }
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/report/totalFineCollected")
    public ResponseEntity getTotalCollectedFineBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        Date startSqlDate = Date.valueOf(startDate);
        Date endSqlDate = Date.valueOf(endDate);
        double fineAmount = transactionService.getTotalCollectedFine(startSqlDate, endSqlDate);
        return ResponseEntity.ok(
                CustomResponseEntity.keyValuePairsToMap("fineAmount", fineAmount)
        );
    }

    @GetMapping("/report/totalStudentsSignedUp")
    public ResponseEntity getTotalStudentsSignedUps(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate signedUpDate
    ) {
        Date sqlDate = Date.valueOf(signedUpDate);
        var signedUpsCardsId = transactionService.getTotalStudentsSignedUps(sqlDate);;
        return ResponseEntity.ok(
                CustomResponseEntity.keyValuePairsToMap(
                        "totalStudentsSignedUps", signedUpsCardsId.size(),
                        "signedUpsCardsId", signedUpsCardsId
                )
        );
    }

}