package com.sclab.library.service;

import com.sclab.library.entity.Author;
import com.sclab.library.repository.AuthorRepository;
import com.sclab.library.util.CustomResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthorService {
    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);

    @Autowired
    AuthorRepository authorRepository;

    public ResponseEntity createAuthor(Author authorRequest) {
        Author authorResp = authorRepository.save(authorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorResp);
    }

    @Cacheable(cacheNames = "authors", key = "#id")
    public Author getAuthor(String id) {
        logger.info("fetching author from db");
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            return author.get();
        } else {
            return new Author();
        }
    }

    @CachePut(cacheNames = "authors", key = "#author.id")
    public Author update(String id, Author author) {
        var optAuthor = authorRepository.findById(id);
        if (optAuthor.isPresent()) {
            Author existingAuthor = optAuthor.get();
            author.setId(id);
            Author updatedAuthor = existingAuthor.setAuthorOrDefault(author);
            return authorRepository.save(updatedAuthor);
        }
        return new Author();
    }

    @CacheEvict(cacheNames = "authors", key = "#id")
    public ResponseEntity delete(String id) {
        var optAuthor = authorRepository.findById(id);
        if (optAuthor.isEmpty()) {
            return CustomResponseEntity.NOT_FOUND(
                    "author", id,
                    "message", "author not found.",
                    "httpCode", HttpStatus.NOT_FOUND.value()
            );
        }
        authorRepository.deleteById(id);
        return CustomResponseEntity.NO_CONTENT();
    }

}