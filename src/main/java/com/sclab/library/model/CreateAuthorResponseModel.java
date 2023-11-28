package com.sclab.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@Data
@NoArgsConstructor
public class CreateAuthorResponseModel {
    private String id;
    private String name;
    private int numberOfPages;
    private String language;
    private boolean available;
    private String genre;
    private String isbnNumber;
    private Date publishedDate;
}