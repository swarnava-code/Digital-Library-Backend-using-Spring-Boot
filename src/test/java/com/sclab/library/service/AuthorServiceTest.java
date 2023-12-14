package com.sclab.library.service;

import com.sclab.library.StubProvider;
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

    private final StubProvider stubProvider = new StubProvider();

    private final Author authorStub = stubProvider.getAuthorStub();

    @Test
    public void testGetAuthor() {

        // mocking repo which will call by service method
        Mockito.when(authorRepository.findById(authorStub.getId())).thenReturn(Optional.ofNullable(authorStub));

        // act (calling service method)
        var author = authorService.getAuthor(authorStub.getId());
        System.out.println(String.format("expected: %s,\nactual: %s", authorStub, author));

        // assertion (assert all data)
        assertEquals(authorStub, author);

        // verify uses
        // verify -> (1) authorService should access authorRepo one time, (2) called findById() with specific id
        // if not it will fail and print result
        verify(authorRepository, times(1)).findById(authorStub.getId());
    }

}