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

    public Student setStudentFieldsOrDefault(Student newStudent) {
        if (newStudent == null) {
            return null;
        }
        this.setIdOrDefault(newStudent.getId());
        this.setAgeOrDefault(newStudent.getAge());
        this.setNameOrDefault(newStudent.getName());
        this.setCountryOrDefault(newStudent.getCountry());
        this.setEmailOrDefault(newStudent.getEmail());
        this.setPhoneNumberOrDefault(newStudent.getPhoneNumber());
        this.setCreatedOnOrDefault(newStudent.getCreatedOn());
        this.setUpdatedOnOrDefault(newStudent.getUpdatedOn());
        return this;
    }

    private void setIdOrDefault(String id) {
        if (id != null) {
            this.id = id;
        }
    }

    private void setAgeOrDefault(Integer age) {
        if (age != null) {
            this.age = age;
        }
    }

    private void setNameOrDefault(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    private void setCountryOrDefault(String country) {
        if (country != null) {
            this.country = country;
        }
    }

    private void setEmailOrDefault(String email) {
        if (email != null) {
            this.email = email;
        }
    }

    private void setPhoneNumberOrDefault(String phoneNumber) {
        if (phoneNumber != null) {
            this.phoneNumber = phoneNumber;
        }
    }

    private void setCreatedOnOrDefault(Date createdOn) {
        if (createdOn != null) {
            this.createdOn = createdOn;
        }
    }

    private void setUpdatedOnOrDefault(Date updatedOn) {
        if (updatedOn != null) {
            this.updatedOn = updatedOn;
        }
    }
}