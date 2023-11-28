package com.sclab.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerRequestModel {
	private int id;
	private String name;
	private String username;
	private String domain;
	private String address;
}