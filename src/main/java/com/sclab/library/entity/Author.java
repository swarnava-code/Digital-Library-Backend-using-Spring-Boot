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

//    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
//    @JsonIgnore //at a time either book or author can be visible
//    private List<Book> books;

    @ManyToMany(mappedBy = "authors", cascade = CascadeType.ALL)
    private List<Book> books;
}