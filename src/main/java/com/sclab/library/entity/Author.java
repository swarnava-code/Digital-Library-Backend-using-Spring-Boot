package com.sclab.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import java.util.List;
import java.util.Objects;

@Entity
@Table
@Data
public class Author {
    @Id
    @UuidGenerator
    private String id;
    private String name;
    private String email;
    private String age;
    private String country;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<AuthorBook> authorBooks;

    @ManyToMany(mappedBy = "authors", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Book> books;
    ////////////////



    public Author setAuthorOrDefault(Author newAuthor) {
        if (newAuthor == null) {
            return null;
        }
        this.name = defaultIfNull(newAuthor.getName(), this.name);
        this.email = defaultIfNull(newAuthor.getEmail(), this.email);
        this.age = defaultIfNull(newAuthor.getAge(), this.age);
        this.country = defaultIfNull(newAuthor.getCountry(), this.country);
        this.authorBooks = defaultIfNull(newAuthor.getAuthorBooks(), this.authorBooks);
        this.books = defaultIfNull(newAuthor.getBooks(), this.books);

        return this;
    }

    private <T> T defaultIfNull(T newValue, T currentValue) {
        return Objects.requireNonNullElse(newValue, currentValue);
    }

    // Helper method to set lists only if they are not null

//    private Author setAuthorBooks(List<AuthorBook> authorBooks) {
//        return authorBooks != null ? this : this
//    }
}