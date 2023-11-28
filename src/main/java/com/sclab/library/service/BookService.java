package com.sclab.library.service;

import com.sclab.library.entity.Book;
import com.sclab.library.model.CreateAuthorResponseModel;
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
//        Author author = authorRepository.findById(authorId).get();
//        var authors = new List<Author>
//        bookRequest.setAuthors(author);
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
//        var books = author.getBooks();
//        books.add(book);
//        author.setBooks(books);
//        authorRepository.save(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
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

}