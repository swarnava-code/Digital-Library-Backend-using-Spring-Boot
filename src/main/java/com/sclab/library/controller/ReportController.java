package com.sclab.library.controller;

import com.sclab.library.entity.Transaction;
import com.sclab.library.enumeration.CardStatus;
import com.sclab.library.enumeration.TransactionStatus;
import com.sclab.library.service.StudentService;
import com.sclab.library.service.TransactionService;
import com.sclab.library.util.CustomResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    StudentService studentService;

    @GetMapping("/transaction")
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
        return ResponseEntity.ok(
                CustomResponseEntity.keyValuePairsToMap(
                        "size", transactions.size(),
                        "transactions", transactions
                )
        );
    }

    @GetMapping("/totalFineCollected")
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

    @GetMapping("/totalStudentsSignedUp")
    public ResponseEntity getTotalStudentsSignedUps(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate signedUpDate
    ) {
        Date sqlDate = Date.valueOf(signedUpDate);
        var signedUpsCardsId = transactionService.getTotalStudentsSignedUps(sqlDate);
        ;
        return ResponseEntity.ok(
                CustomResponseEntity.keyValuePairsToMap(
                        "totalStudentsSignedUps", signedUpsCardsId.size(),
                        "signedUpsCardsId", signedUpsCardsId
                )
        );
    }

    @GetMapping("/findStudentsByCardStatus")
    public ResponseEntity findStudentsByCardStatus(@RequestParam String cardStatus) {
        CardStatus cardStatus1 = CardStatus.ACTIVE;
        if (cardStatus.equalsIgnoreCase("Active")) {
            cardStatus1 = CardStatus.ACTIVE;
        } else if (cardStatus.equalsIgnoreCase(CardStatus.EXPIRED.name())) {
            cardStatus1 = CardStatus.EXPIRED;
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    CustomResponseEntity.keyValuePairsToMap(
                            "error", String.format("invalid cardStatus '%s'", cardStatus),
                            "acceptedValues", CardStatus.values()
                    )
            );
        }
        var activeStudents = studentService.findStudentsByCardStatus(cardStatus1);
        return ResponseEntity.ok(
                CustomResponseEntity.keyValuePairsToMap(
                        "size", activeStudents.size(),
                        "activeStudents", activeStudents
                )
        );
    }

    @GetMapping("/findStudentsByTotalIssuedBook")
    public ResponseEntity findStudentsByTotalIssuedBook(@RequestParam int totalIssuedBook) {
        var students = studentService.findStudentsByTotalIssuedBook(totalIssuedBook);
        return ResponseEntity.ok(
                CustomResponseEntity.keyValuePairsToMap(
                        "size", students.size(),
                        "students", students
                )
        );
    }

}