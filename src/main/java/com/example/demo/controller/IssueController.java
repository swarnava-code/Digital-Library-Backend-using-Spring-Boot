package com.example.demo.controller;

import com.example.demo.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IssueController {

    @Autowired
    IssueService issueService;

    @PostMapping("/issue")
    public ResponseEntity issueBook(
            @RequestParam String cardId,
            @RequestParam String bookId
    ) {
        // Call the service to issue the book
        ResponseEntity responseEntity = issueService.issueBook(cardId, bookId);
        return responseEntity;
    }
}
