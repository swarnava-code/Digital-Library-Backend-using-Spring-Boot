package com.sclab.library.service;

import com.sclab.library.entity.Author;
import com.sclab.library.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    Author authorStub = Author.builder()
            .id("9428c769-185a-4c6e-b890-ae884b823945")
            .name("Swarnava Chakraborty")
            .email("swarnava.code@gmail.com")
            .age(26)
            .country("India")
            .build();

    Author authorStub2 = Author.builder()
            .id("9428c769-185a-4c6e-b890-ae884b777945")
                .name("Swetank Raj")
                .email("iamswetank@gmail.com")
                .age(26)
                .country("India")
                .build();

    @Test
    public void testGetAuthor() {
        // mocking repo which will call by service indirectly
        Mockito.when(authorRepository.findById(authorStub.getId())).thenReturn(Optional.ofNullable(authorStub));
        // act
        var author = authorService.getAuthor(authorStub.getId());
        System.out.println(String.format("expected: %s,\nactual: %s", authorStub, author));
        // assertion
        assertEquals(authorStub, author);
        // verify that authorService should access authorRepo,
        // and check if it call or not to findById() with particular id 1 time
        // if not it will show how service used repo
        verify(authorRepository, times(1)).findById(authorStub.getId());
    }

}