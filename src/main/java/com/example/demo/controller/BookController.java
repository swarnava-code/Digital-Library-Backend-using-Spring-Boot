package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.entity.Transaction;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping("/book")
    public ResponseEntity createBook(@RequestBody Book book){
        return bookService.createBook(book);
    }


    @GetMapping("/book")
    public ResponseEntity<List<Book>> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBook(@PathVariable String id){
        return bookService.getBookById(id);
    }

}