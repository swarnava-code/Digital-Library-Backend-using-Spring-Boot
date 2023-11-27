package com.sclab.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import java.util.List;

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
}