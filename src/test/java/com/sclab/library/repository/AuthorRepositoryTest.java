package com.sclab.library.repository;

import com.sclab.library.entity.Author;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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

    static Author authorStub;

    static Author authorStub2;

    @BeforeAll
    public static void setUpStub() {
        authorStub = Author.builder()
                .id("9428c769-185a-4c6e-b890-ae884b823945")
                .name("Swarnava Chakraborty")
                .email("swarnava.code@gmail.com")
                .age(26)
                .country("India")
                .build();
        authorStub2 = Author.builder()
                .id("9428c769-185a-4c6e-b890-ae884b777945")
                .name("Swetank Raj")
                .email("iamswetank@gmail.com")
                .age(26)
                .country("India")
                .build();
    }

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

    @AfterAll
    public static void tearDown() {
        authorStub = null;
        authorStub2 = null;
    }

}