package com.sclab.library.controller;

import com.sclab.library.entity.Book;
import com.sclab.library.entity.Card;
import com.sclab.library.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class CardController {

    @Autowired
    CardService cardService;

    @PostMapping("/card")
    public ResponseEntity createCard(@RequestBody Card card){
        return cardService.createCard(card);
    }

    @GetMapping("/card")
    public ResponseEntity<List<Card>> getAllCard(){
        return cardService.getAllCards();
    }

    @GetMapping("/card/{id}")
    public ResponseEntity<Book> getSingleCard(@PathVariable String id){
        return cardService.getById(id);
    }

}