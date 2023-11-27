package com.sclab.library.service;

import com.sclab.library.entity.Author;
import com.sclab.library.model.CustomMessage;
import com.sclab.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public ResponseEntity createAuthor(Author authorRequest) {
        Author authorResp = authorRepository.save(authorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorResp);
    }

    public ResponseEntity getAuthorById(String id) {
        Optional<Author> author = authorRepository.findById(id);
        if(author.isEmpty()){
            CustomMessage customMessage = new CustomMessage();
            customMessage.setMessage("Book not found with id: "+id);
            customMessage.setStatusCode(404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customMessage);
        }
        return ResponseEntity.status(HttpStatus.OK).body(author);
    }

}