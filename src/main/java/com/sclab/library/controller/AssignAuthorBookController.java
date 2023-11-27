package com.sclab.library.controller;

import com.sclab.library.service.AssignAuthorBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssignAuthorBookController {
    @Autowired
    AssignAuthorBookService assignAuthorBookService;

    @PostMapping("/book/{bookId}/author/{authorId}")
    public ResponseEntity assignAuthor(
            @PathVariable String bookId,
            @PathVariable String authorId
    ) {
        System.out.println("ho66e...");
        return assignAuthorBookService.assignAuthorToBook(bookId, authorId);
    }

}