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
}