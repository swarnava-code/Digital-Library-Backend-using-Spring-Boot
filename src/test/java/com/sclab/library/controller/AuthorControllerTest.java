package com.sclab.library.controller;

import com.sclab.library.StubProvider;
import com.sclab.library.entity.Author;
import com.sclab.library.repository.AuthorRepository;
import com.sclab.library.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
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

    private final StubProvider stubProvider = new StubProvider();

    private  Author authorStub;

    @BeforeEach
    public void setupStub(){
        authorStub = stubProvider.getAuthorStub();
    }

    @Test
    public void testGetAuthorById() throws Exception {
        given(authorService.getAuthor(authorStub.getId())).willReturn(authorStub);
        mockMvc.perform(get("/author/{authorId}", authorStub.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(authorStub.getId()))
                .andReturn();
    }

}