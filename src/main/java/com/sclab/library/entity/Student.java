package com.sclab.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Date;

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
    private String email;
    private String phoneNumber;
    private Date createdOn;
    private Date updatedOn;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    @JsonIgnore
    private Card card;
}