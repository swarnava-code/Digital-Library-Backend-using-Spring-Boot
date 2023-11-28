package com.sclab.library.service;

import com.sclab.library.entity.AuthorBook;
import com.sclab.library.entity.Book;
import com.sclab.library.util.CustomResponseEntity;
import com.sclab.library.repository.AuthorBookRepository;
import com.sclab.library.repository.AuthorRepository;
import com.sclab.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AssignAuthorBookService {
    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorBookRepository authorBookRepository;

    public ResponseEntity assignAuthorToBook(String bookId, String authorId) {
        var optBook = bookRepository.findById(bookId);
        var optAuthor = authorRepository.findById(authorId);
        if (optBook.isPresent() && optAuthor.isPresent()) {
            Book book = optBook.get();
            var authors = book.getAuthors();
            authors.add(optAuthor.get());
            book.setAuthors(authors);
            AuthorBook authorBook = new AuthorBook();
            authorBook.setAuthor(optAuthor.get());
            authorBook.setBook(optBook.get());
            authorBookRepository.save(authorBook);
            return CustomResponseEntity.CUSTOM_MSG_BODY(201, book);
        }
        return CustomResponseEntity.NOT_FOUND("optBook:"+optBook.isEmpty()+" , optAuthor:"+optAuthor.isEmpty());
    }

}