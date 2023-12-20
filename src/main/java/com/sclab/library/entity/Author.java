package com.sclab.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author implements Serializable {

    @Id
    @UuidGenerator
    private String id;

    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    @Size(min = 2, max = 35)
    @NotNull
    @NotBlank
    private String name;

    @Column(unique = true)
    @NotNull
    @Email
    private String email;

    @Min(value = 1, message = "Zero or negative value is not possible")
    @Max(value = 123, message = "122 years and 164 days is the longest documented and verified human lifespan")
    private int age;

    @Pattern(regexp = "^[a-zA-Z -]+$")
    @Size(min = 3, max = 35)
    private String country;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private List<AuthorBook> authorBooks;

    @ManyToMany(mappedBy = "authors", cascade = CascadeType.ALL)
    @JsonIgnore
    @Setter(AccessLevel.NONE)
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