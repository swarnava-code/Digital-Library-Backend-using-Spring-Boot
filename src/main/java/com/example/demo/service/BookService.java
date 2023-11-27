package com.example.demo.service;

import com.example.demo.entity.Book;
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
public class BookService {
    Configuration cfg;
    SessionFactory sessionFactory;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private BookRepository bookRepository;

    public BookService() {
//        cfg = new Configuration();
//        sessionFactory = cfg.configure("hbm.cfg.xml").buildSessionFactory();
    }

    public ResponseEntity createBook(Book bookRequest) {
        Book book = bookRepository.save(bookRequest);
        CreateAuthorResponseModel responseBody = new CreateAuthorResponseModel();
        responseBody.setId(book.getId());
        responseBody.setName(book.getName());
        responseBody.setNumberOfPages(book.getNumberOfPages());
        responseBody.setLanguage(book.getLanguage());
        responseBody.setAvailable(book.isAvailable());
        responseBody.setGenre(book.getGenre());
        responseBody.setIsbnNumber(book.getIsbnNumber());
        responseBody.setPublishedDate(book.getPublishedDate());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    public ResponseEntity getBookById(String id) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isEmpty()){
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setMessage("Book not found with id: "+id);
            errorMessage.setStatusCode(404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    public ResponseEntity getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }


}