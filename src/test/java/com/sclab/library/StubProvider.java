package com.sclab.library;

import com.sclab.library.entity.Author;
import lombok.Getter;

@Getter
public class StubProvider {

    private final Author authorStub = Author.builder()
            .id("9428c769-185a-4c6e-b890-ae884b823945")
            .name("Swarnava Chakraborty")
            .email("swarnava.code@gmail.com")
            .age(26)
            .country("India")
            .build();

    private final Author authorStub2 = Author.builder()
            .id("9428c769-185a-4c6e-b890-ae884b777945")
            .name("Swetank Raj")
            .email("iamswetank@gmail.com")
            .age(26)
            .country("India")
            .build();

}