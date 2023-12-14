package com.sclab.library.repository;

import com.sclab.library.StubProvider;
import com.sclab.library.entity.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    private final StubProvider stubProvider = new StubProvider();

    private final Author authorStub = stubProvider.getAuthorStub();

    private final Author authorStub2 = stubProvider.getAuthorStub2();

    @Test
    public void testFindById__whenIdIsValid() {
        authorRepository.save(authorStub);
        var optAuthor = authorRepository.findById(authorStub.getId());
        assertTrue(optAuthor.isPresent());
        assertEquals(authorStub, optAuthor.get());
    }

    @Test
    public void testFindById_whenIdIsInvalid() {
        var author = authorRepository.findById("123XYZ");
        assertFalse(author.isPresent());
    }

    @Test
    public void testFindAll_whenSize0() {
        var authors = authorRepository.findAll();
        assertEquals(0, authors.size());
    }

    @Test
    public void testFindAll_whenSize2() {
        List<Author> expectedAuthorsStub = new ArrayList<>();
        authorRepository.save(authorStub);
        authorRepository.save(authorStub2);
        var optAuthor1 = authorRepository.findById(authorStub.getId());
        var optAuthor2 = authorRepository.findById(authorStub2.getId());
        assertTrue(optAuthor1.isPresent());
        assertTrue(optAuthor2.isPresent());
        Collections.addAll(expectedAuthorsStub, authorStub, authorStub2);
        var actualAuthors = authorRepository.findAll();
        assertEquals(expectedAuthorsStub.size(), actualAuthors.size());
        assertEquals(expectedAuthorsStub, actualAuthors);
    }

}