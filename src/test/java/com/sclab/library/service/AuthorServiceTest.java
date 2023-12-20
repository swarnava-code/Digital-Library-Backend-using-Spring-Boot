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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        Mockito.when(authorRepository.findById(authorStub.getId()))
                .thenReturn(Optional.ofNullable(authorStub));
        var author = authorService.getAuthor(authorStub.getId());
        System.out.println(String.format("expected: %s,\nactual: %s", authorStub, author));
        assertEquals(authorStub, author);
        verify(authorRepository, times(1)).findById(authorStub.getId());
    }

    @Test
    public void testCreateAuthor() {
        Mockito.when(authorRepository.save(authorStub))
                .thenReturn(authorStub);
        var author = authorService.createAuthor(authorStub);
        var expected = ResponseEntity.status(HttpStatus.CREATED).body(authorStub);
        assertEquals(expected, author);
        verify(authorRepository, times(1)).save(authorStub);
    }

    @Test
    public void testUpdateAuthor() {
        Mockito.when(authorRepository.findById(authorStub.getId()))
                .thenReturn(Optional.of(authorStub));
        Mockito.when(authorRepository.save(authorStub))
                .thenReturn(authorStub);

//        Mockito.when(authorStub.setAuthorOrDefault(authorStub))
//                .thenReturn(authorStub);

        var author = authorService.update(authorStub.getId(), authorStub);

//        var expected = ResponseEntity.status(HttpStatus.CREATED).body(authorStub);
        assertEquals(authorStub, author);

        verify(authorRepository, times(1)).findById(authorStub.getId());
        verify(authorRepository, times(1)).save(authorStub);
    }

    public Author update(String id, Author author) {
        var optAuthor = authorRepository.findById(id);
        if (optAuthor.isPresent()) {
            Author existingAuthor = optAuthor.get();
            author.setId(id);
            Author updatedAuthor = existingAuthor.setAuthorOrDefault(author);
            return authorRepository.save(updatedAuthor);
        }
        return new Author();
    }

}