package com.sclab.library.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table
@Data
@NoArgsConstructor
public class Student {

    @Id
    @UuidGenerator
    private String id;

    private int age;
    private String name;
    private String country;

    @Column(unique = true)
    @Email
    @NotBlank
    private String email;

    private String phoneNumber;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @CreationTimestamp
    private Date createdOn;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @UpdateTimestamp
    private Date updatedOn;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Card card;

    public Student setStudentOrDefault(Student newStudent) {
        if (newStudent == null) {
            return null;
        }
        this.id = Objects.requireNonNullElse(newStudent.id, this.id);
        this.age = Objects.requireNonNullElse(newStudent.age, this.age);
        this.name = Objects.requireNonNullElse(newStudent.name, this.name);
        this.country = Objects.requireNonNullElse(newStudent.country, this.country);
        this.email = Objects.requireNonNullElse(newStudent.email, this.email);
        this.phoneNumber = Objects.requireNonNullElse(newStudent.phoneNumber, this.phoneNumber);
        return this;
    }

}