package com.example.demo.controller;

import com.example.demo.entity.Author;
import com.example.demo.model.CreateAuthorRequestModel;
import com.example.demo.service.AuthorService;
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
    public ResponseEntity getBook(@PathVariable Long id){
        return authorService.getAuthorById(id);
    }

}