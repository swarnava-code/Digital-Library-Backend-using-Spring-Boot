package com.example.demo.service;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.entity.Card;
import com.example.demo.entity.Transaction;
import com.example.demo.model.CreateAuthorResponseModel;
import com.example.demo.model.ErrorMessage;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CardRepository;
import com.example.demo.repository.TransactionRepository;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public CardService() {
//        cfg = new Configuration();
//        sessionFactory = cfg.configure("hbm.cfg.xml").buildSessionFactory();
    }

    public ResponseEntity createCard(Card cardRequest) {
        Card card = cardRepository.save(cardRequest);
//        Card responseBody = new Card();
//        responseBody.setId(book.getId());
//        responseBody.setName(book.getName());
//        responseBody.setNumberOfPages(book.getNumberOfPages());
//        responseBody.setLanguage(book.getLanguage());
//        responseBody.setAvailable(book.isAvailable());
//        responseBody.setGenre(book.getGenre());
//        responseBody.setIsbnNumber(book.getIsbnNumber());
//        responseBody.setPublishedDate(book.getPublishedDate());
        return ResponseEntity.status(HttpStatus.CREATED).body(card);
    }


    public ResponseEntity getById(String id) {
        Optional<Card> card = cardRepository.findById(id);
        if(card.isEmpty()){
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setMessage("Not found with this id: "+id);
            errorMessage.setStatusCode(404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
        return ResponseEntity.status(HttpStatus.OK).body(card);
    }

    public ResponseEntity getAllCards() {
        List<Card> cards = cardRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(cards);
    }

}