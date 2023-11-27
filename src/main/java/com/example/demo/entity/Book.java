package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table
//@Data
//@NoArgsConstructor
public class Book implements Serializable {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @UuidGenerator
    private String id;
    private String name;
    private int numberOfPages;
    private String language;
    private boolean available;
    private String genre;
    private String isbnNumber;
    private Date publishedDate;

    @OneToOne
    private Author author;

    @OneToMany(mappedBy = "book")
    private List<Transaction> transactions;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setIsbnNumber(String isbnNumber) {
        this.isbnNumber = isbnNumber;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getGenre() {
        return genre;
    }

    public String getIsbnNumber() {
        return isbnNumber;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public Author getAuthor() {
        return author;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }


}