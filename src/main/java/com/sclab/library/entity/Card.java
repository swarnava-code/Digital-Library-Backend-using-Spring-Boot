package com.sclab.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@Entity
@Table
@Data
public class Card implements Serializable {

    @Id
    @UuidGenerator
    private String id;

    private String status;
    private String email;
    private Date validUpto;
    private Date createdOn;
    private Date updatedOn;
    private int totalIssuedBook;

    @OneToMany(mappedBy = "card")
    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<Transaction> transactions;

}