package com.sclab.library.controller;

import ch.qos.logback.core.testUtil.RandomUtil;
import com.sclab.library.entity.Book;
import com.sclab.library.service.BookService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;

    //@RolesAllowed("USER")
    @PostMapping
    public ResponseEntity create(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> get(@PathVariable String id) {
        return bookService.getBookById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable String id,
                                 @RequestBody Book book) {
        return bookService.update(id, book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        return bookService.delete(id);
    }

    /**
     * Demo of Circuit Breaker Design Pattern
     * @return
     */
    @CircuitBreaker(name = "bookControllerCircuitBreaker", fallbackMethod = "fallbackResponse")
    @GetMapping("/hello")
    public String demoOfCircuitBreaker() {
        int oo = RandomUtil.getPositiveInt();
        if (RandomUtil.getPositiveInt() < 711111111) {
            throw new RuntimeException("oh my god, Covid virus attack our internal system and databases, " +
                    "Please pray for us");
        }
        return "Working " + oo;
    }

    public ResponseEntity<String> fallbackResponse(Throwable throwable) {
        return ResponseEntity.internalServerError().body("Please contact with Developer: " + throwable.getMessage());
    }
}