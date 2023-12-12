package com.sclab.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sclab.library.enumeration.Genre;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
@Data
public class Book implements Serializable {

    @Id
    @UuidGenerator
    private String id;

    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    @Size(min = 2, max = 50)
    @NotNull
    @NotBlank
    private String name;

    @Min(1)
    private int numberOfPages;

    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    @Size(min = 2, max = 25)
    @NotBlank
    private String language;

    private boolean available;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Pattern(regexp = "^\\d{13}$")
    private String isbnNumber;

    private Date publishedDate;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<Author> authors;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private Set<AuthorBook> authorBooks;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private Set<Transaction> transactions;

    public Book setBookOrDefault(Book newBook) {
        if (newBook == null) {
            return null;
        }
        this.name = Objects.requireNonNullElse(newBook.name, this.name);
        this.numberOfPages = (newBook.numberOfPages != 0) ? newBook.numberOfPages : this.numberOfPages;
        this.language = Objects.requireNonNullElse(newBook.language, this.language);
        this.available = newBook.available || this.available;
        this.genre = Objects.requireNonNullElse(newBook.genre, this.genre);
        this.isbnNumber = Objects.requireNonNullElse(newBook.isbnNumber, this.isbnNumber);
        this.publishedDate = Objects.requireNonNullElse(newBook.publishedDate, this.publishedDate);
        this.authors = Objects.requireNonNullElse(newBook.authors, this.authors);
        this.authorBooks = Objects.requireNonNullElse(newBook.authorBooks, this.authorBooks);
        this.transactions = Objects.requireNonNullElse(newBook.transactions, this.transactions);
        return this;
    }

}