package com.sclab.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateAuthorRequestModel {
    private String name;
    private String email;
    private String age;
    private String country;
}