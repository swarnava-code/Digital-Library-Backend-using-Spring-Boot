package com.sclab.library.service;

import com.sclab.library.entity.Card;
import com.sclab.library.model.CustomMessage;
import com.sclab.library.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    public ResponseEntity createCard(Card cardRequest) {
        Card card = cardRepository.save(cardRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(card);
    }

    public ResponseEntity getById(String id) {
        Optional<Card> card = cardRepository.findById(id);
        if(card.isEmpty()){
            CustomMessage customMessage = new CustomMessage();
            customMessage.setMessage("Not found with this id: "+id);
            customMessage.setStatusCode(404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customMessage);
        }
        return ResponseEntity.status(HttpStatus.OK).body(card);
    }

    public ResponseEntity getAllCards() {
        List<Card> cards = cardRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(cards);
    }

}