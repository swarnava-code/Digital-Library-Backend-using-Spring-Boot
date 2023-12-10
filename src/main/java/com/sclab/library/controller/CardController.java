package com.sclab.library.controller;

import com.sclab.library.entity.Book;
import com.sclab.library.entity.Card;
import com.sclab.library.service.CardService;
import com.sclab.library.util.CustomMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    CardService cardService;

    @PostMapping("/{studentId}")
    public ResponseEntity issueCard(
            @RequestBody Card card,
            @PathVariable String studentId
    ) {
        return cardService.createCard(card, studentId);
    }

    @GetMapping
    public ResponseEntity<List<Card>> getAllCard() {
        return cardService.getAllCards();
    }

    @GetMapping("/{id}")
    public ResponseEntity getSingleCard(@PathVariable String id) {
        Card card = cardService.getById(id);
        if (card.getId() == null) {
            CustomMessage customMessage = new CustomMessage();
            customMessage.setMessage("Not found with this id: " + id);
            customMessage.setStatusCode(404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customMessage);
        }
        return ResponseEntity.status(HttpStatus.OK).body(card);
    }

}