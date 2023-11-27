package com.example.demo.service;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.model.CreateAuthorRequestModel;
import com.example.demo.model.CreateAuthorResponseModel;
import com.example.demo.repository.AuthorRepository;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
//    Configuration cfg;
//    SessionFactory sessionFactory;
    @Autowired
    AuthorRepository authorRepository;

//    public AuthorService() {
//        cfg = new Configuration();
//        sessionFactory = cfg.configure("hbm.cfg.xml").buildSessionFactory();
//    }

    public ResponseEntity createAuthor(Author authorRequest) {

//        Author author = new Author();
//        author.setName(authorRequest.getName());
//        author.setEmail(authorRequest.getEmail());
//        author.setAge(authorRequest.getAge());
//        author.setCountry(authorRequest.getCountry());

        Author authorResp = authorRepository.save(authorRequest);


        return ResponseEntity.status(HttpStatus.CREATED).body(authorResp);
    }

    public ResponseEntity getAuthorById(Long id) {
        Author author = authorRepository.getReferenceById(id);
        return ResponseEntity.status(HttpStatus.OK).body(author);
    }

}