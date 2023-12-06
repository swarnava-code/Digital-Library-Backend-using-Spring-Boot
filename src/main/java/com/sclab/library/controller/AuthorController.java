package com.sclab.library.controller;

import com.sclab.library.entity.Author;
import com.sclab.library.service.AuthorService;
import com.sclab.library.util.CustomResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @PostMapping("/author")
    public ResponseEntity create(@RequestBody Author author) {
        return authorService.createAuthor(author);
    }

    @GetMapping("/author/{authorId}")
    public Object get(@PathVariable String authorId) {
        Author author = authorService.getAuthor(authorId);
        if (author.getId() == null) {
            return CustomResponseEntity.NOT_FOUND(
                    "authorId", authorId,
                    "message", "not found",
                    "httpCode", HttpStatus.NOT_FOUND.value(),
                    "entity", Author.class.getName()
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(author);
    }

    @PutMapping("/author/{id}")
    public ResponseEntity update(@PathVariable String id,
                                 @RequestBody Author author) {
        Author updatedAuthor = authorService.update(id, author);
        if(updatedAuthor.getId()==null){
            return CustomResponseEntity.NOT_FOUND();
        }
        return CustomResponseEntity.CUSTOM_MSG(200, updatedAuthor);

    }

    @DeleteMapping("/author/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        return authorService.delete(id);
    }

}