package com.sclab.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

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

    @OneToMany(mappedBy = "card")
    @JsonIgnore
    private List<Transaction> transactions;
}