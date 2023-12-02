package com.sclab.library.service;

import com.sclab.library.entity.Book;
import com.sclab.library.util.CustomResponseEntity;
import com.sclab.library.repository.AuthorRepository;
import com.sclab.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public ResponseEntity createBook(Book bookRequest) {
        Book book = bookRepository.save(bookRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    public ResponseEntity getBookById(String id) {
        Optional<Book> optBook = bookRepository.findById(id);
        if (optBook.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(optBook);
        }
        return CustomResponseEntity.NOT_FOUND("Book not found with id: " + id);
    }

    public ResponseEntity getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    public ResponseEntity update(String id, Book book) {
        var optBook = bookRepository.findById(id);
        if (optBook.isPresent()) {
            Book existingBook = optBook.get();
            book.setId(id);
            Book updatedBook = existingBook.setBookOrDefault(book);
            Book replacedBook = bookRepository.save(updatedBook);
            return CustomResponseEntity.CUSTOM_MSG(200, replacedBook);
        }
        return CustomResponseEntity.NOT_FOUND();
    }

    public ResponseEntity delete(String id) {
        var optBook = bookRepository.findById(id);
        if (optBook.isEmpty()) {
            return CustomResponseEntity.NOT_FOUND(
                    "bookId", id,
                    "message", "book not found.",
                    "httpCode", HttpStatus.NOT_FOUND.value()
            );
        }
        bookRepository.deleteById(id);
        return CustomResponseEntity.NO_CONTENT();
    }

}