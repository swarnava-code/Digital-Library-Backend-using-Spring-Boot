package com.sclab.library.controller;

import com.sclab.library.entity.Book;
import com.sclab.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping("/book")
    public ResponseEntity create(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @GetMapping("/book")
    public ResponseEntity<List<Book>> getAll() {
        return bookService.getAllBooks();
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> get(@PathVariable String id) {
        return bookService.getBookById(id);
    }

    @PutMapping("/book/{id}")
    public ResponseEntity update(@PathVariable String id,
                                        @RequestBody Book book){
        return bookService.update(id, book);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity delete(@PathVariable String id){
        return bookService.delete(id);
    }
}