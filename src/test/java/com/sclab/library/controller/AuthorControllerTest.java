package com.sclab.library.controller;

import com.sclab.library.StubProvider;
import com.sclab.library.entity.Author;
import com.sclab.library.repository.AuthorRepository;
import com.sclab.library.service.AuthorService;
import com.sclab.library.util.CustomResponseEntity;
import com.sclab.library.util.JSONUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    private Author authorStub;

    @BeforeEach
    public void setupStub() {
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

    @Test
    public void testUpdateAuthorById() throws Exception {
        authorStub.setCountry("Japan");
        given(authorService.update(authorStub.getId(), authorStub)).willReturn(authorStub);
        mockMvc.perform(
                        put("/author/{authorId}", authorStub.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JSONUtil.objectToJson(authorStub))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(authorStub.getId()))
                .andReturn();
    }

    @Test
    public void testCreateAuthor() throws Exception {
        given(authorService.createAuthor(authorStub))
                .willReturn(ResponseEntity.status(HttpStatus.CREATED).body(authorStub));
        mockMvc.perform(
                        post("/author")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JSONUtil.objectToJson(authorStub))
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(authorStub.getId()))
                .andReturn();
    }

    @Test
    public void testDeleteAuthorById() throws Exception {
        given(authorService.delete(authorStub.getId())).willReturn(CustomResponseEntity.NO_CONTENT());
        mockMvc.perform(
                        delete("/author/{authorId}", authorStub.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(204))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void testUnsuccessfulDeleteAuthorById() throws Exception {
        String expectedResponse = JSONUtil.objectToJson(CustomResponseEntity.NOT_FOUND(
                "author", authorStub.getId(),
                "message", "author not found.",
                "httpCode", HttpStatus.NOT_FOUND.value()
        ));
        given(authorService.delete(authorStub.getId()))
                .willReturn(CustomResponseEntity.NOT_FOUND(
                        "author", authorStub.getId(),
                        "message", "author not found.",
                        "httpCode", HttpStatus.NOT_FOUND.value()
                ));
        mockMvc.perform(
                        delete("/author/{authorId}", authorStub.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(expectedResponse)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

}