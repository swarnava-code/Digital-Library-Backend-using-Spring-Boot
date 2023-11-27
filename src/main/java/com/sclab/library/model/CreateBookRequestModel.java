package com.sclab.library.model;

import lombok.Data;
import java.sql.Date;

@Data
public class CreateBookRequestModel {
	private String name;
	private int numberOfPages;
	private String language;
	private boolean available;
	private String genre;
	private String isbnNumber;
	private Date publishedDate;
}