package com.sclab.library.service;

import com.sclab.library.entity.Author;
import com.sclab.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public ResponseEntity createAuthor(Author authorRequest) {
        Author authorResp = authorRepository.save(authorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorResp);
    }

    public ResponseEntity getAuthorById(Long id) {
        Author author = authorRepository.getReferenceById(id);
        return ResponseEntity.status(HttpStatus.OK).body(author);
    }

}