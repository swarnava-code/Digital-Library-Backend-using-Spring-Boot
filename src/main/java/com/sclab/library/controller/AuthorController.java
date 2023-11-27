package com.sclab.library.controller;

import com.sclab.library.entity.Author;
import com.sclab.library.entity.Book;
import com.sclab.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @PostMapping("/author")
    public ResponseEntity createBook(@RequestBody Author author){
        return authorService.createAuthor(author);
    }

    @GetMapping("/author/{id}")
    public ResponseEntity getAuthor(@PathVariable String id){
        return authorService.getAuthorById(id);
    }

}