package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.entity.Card;
import com.example.demo.service.BookService;
import com.example.demo.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CardController {

    @Autowired
    CardService cardService;

//    @PostMapping("/issue")
//    public ResponseEntity<String> issueBook(
//            @RequestParam String cardId,
//            @RequestParam String bookId
//    ) {
//        // Call the service to issue the book
//        bookService.issueBook(cardId, bookId);
//
//        return ResponseEntity.ok("Book issued successfully");
//    }

    @PostMapping("/card")
    public ResponseEntity createCard(@RequestBody Card card){
        return cardService.createCard(card);
    }

    @GetMapping("/card")
    public ResponseEntity getAllCard(){
        return cardService.getAllCards();
    }

    @GetMapping("/card/{id}")
    public ResponseEntity<Book> getSingleCard(@PathVariable String id){
        return cardService.getById(id);
    }

}