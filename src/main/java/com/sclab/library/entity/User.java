package com.sclab.library.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

public class User {
    @Id
    @UuidGenerator
    private String id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String authority;
}