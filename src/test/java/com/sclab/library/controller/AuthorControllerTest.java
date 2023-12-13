package com.sclab.library.controller;

import com.sclab.library.entity.Author;
import com.sclab.library.repository.AuthorRepository;
import com.sclab.library.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    @Mock
    private AuthorRepository authorRepository;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private MockMvc mockMvc;

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
    public void testGetAuthorById() throws Exception {
        // mock service response
        given(authorService.getAuthor(authorStub.getId())).willReturn(authorStub);
        mockMvc.perform(get("/author/{authorId}", authorStub.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(authorStub.getId()))
                .andReturn();
    }

}