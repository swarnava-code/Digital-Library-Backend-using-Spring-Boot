package com.sclab.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table
@Data
public class Author implements Serializable {

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

    public Author setAuthorOrDefault(Author newAuthor) {
        if (newAuthor == null) {
            return null;
        }
        this.name = Objects.requireNonNullElse(newAuthor.getName(), this.name);
        this.email = Objects.requireNonNullElse(newAuthor.getEmail(), this.email);
        this.age = Objects.requireNonNullElse(newAuthor.getAge(), this.age);
        this.country = Objects.requireNonNullElse(newAuthor.getCountry(), this.country);
        this.authorBooks = Objects.requireNonNullElse(newAuthor.getAuthorBooks(), this.authorBooks);
        this.books = Objects.requireNonNullElse(newAuthor.getBooks(), this.books);
        return this;
    }

}