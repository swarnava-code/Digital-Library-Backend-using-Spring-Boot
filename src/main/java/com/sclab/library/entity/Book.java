package com.sclab.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table
@Data
public class Book implements Serializable {
    @Id
    @UuidGenerator
    private String id;
    private String name;
    private int numberOfPages;
    private String language;
    private boolean available;
    private String genre;
    private String isbnNumber;
    private Date publishedDate;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List<AuthorBook> authorBooks;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List<Transaction> transactions;

    public Book setBookOrDefault(Book newBook) {
        if (newBook == null) {
            return null;
        }
        this.setName(newBook.getName() != null ? newBook.getName() : this.getName());
        this.setNumberOfPages(newBook.getNumberOfPages() != 0 ? newBook.getNumberOfPages() : this.getNumberOfPages());
        this.setLanguage(newBook.getLanguage() != null ? newBook.getLanguage() : this.getLanguage());
        this.setAvailable(newBook.isAvailable() || this.isAvailable());
        this.setGenre(newBook.getGenre() != null ? newBook.getGenre() : this.getGenre());
        this.setIsbnNumber(newBook.getIsbnNumber() != null ? newBook.getIsbnNumber() : this.getIsbnNumber());
        this.setPublishedDate(newBook.getPublishedDate() != null ? newBook.getPublishedDate() : this.getPublishedDate());
        this.setAuthors(newBook.getAuthors() != null ? newBook.getAuthors() : this.getAuthors());
        this.setAuthorBooks(newBook.getAuthorBooks() != null ? newBook.getAuthorBooks() : this.getAuthorBooks());
        this.setTransactions(newBook.getTransactions() != null ? newBook.getTransactions() : this.getTransactions());
        return this;
    }

}