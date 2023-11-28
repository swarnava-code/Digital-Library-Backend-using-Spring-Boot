package com.sclab.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerResponseModel {
	private int id;
	private String name;
	private String email;
	private int status;
	private String message;
}