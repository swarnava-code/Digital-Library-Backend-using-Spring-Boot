package com.sclab.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.UuidGenerator;

public class User {
    @Id
    @UuidGenerator
    private String id;

    private String name;

    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_\\-.]{2,19}$")
    @Size(min = 2, max = 19)
    private String username;

    @Email
    @NotBlank
    @NotNull
    private String email;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,20}$")
    @Size(min = 8, max = 20)
    private String password;

    private String authority;
}