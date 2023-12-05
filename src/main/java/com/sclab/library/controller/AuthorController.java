package com.sclab.library.controller;

import com.sclab.library.entity.Author;
import com.sclab.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @PostMapping("/author")
    public ResponseEntity create(@RequestBody Author author){
        return authorService.createAuthor(author);
    }

    @GetMapping("/author/{authorId}")
    @Cacheable(value = "author", key = "#authorId", unless = "#result == null")
    public Object get(@PathVariable String authorId){
        // impl. class finder and wrap EntityResponse accordingly for proper status code
//        Object data = getCachedData(authorId);
        Object body = authorService.getAuthorById(authorId).getBody();
        return body;
//        return ResponseEntity.status(HttpStatus.OK).body(data);
//        return authorService.getAuthorById(authorId).getBody();
    }

//    @Cacheable(value = "author", key = "#authorId", unless = "#result == null")
//    public Object getCachedData(String authorId){
//        return authorService.getAuthorById(authorId).getBody();
//    }

    @PutMapping("/author/{id}")
    public ResponseEntity update(@PathVariable String id,
                                 @RequestBody Author author){
        return authorService.update(id, author);
    }

    @DeleteMapping("/author/{id}")
    public ResponseEntity delete(@PathVariable String id){
        return authorService.delete(id);
    }

}