package com.andriokar.database.controllers;

import com.andriokar.database.TestDataUtil;
import com.andriokar.database.domain.entities.AuthorEntity;
import com.andriokar.database.services.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    private final AuthorService authorService;

    private final long FAKE_AUTHOR_UD = 37L;

    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.authorService = authorService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(null);
        String testAuthorAJson = objectMapper.writeValueAsString(testAuthorA);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testAuthorAJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(null);
        String testAuthorAJson = objectMapper.writeValueAsString(testAuthorA);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testAuthorAJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthorA.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthorA.getAge())
        );
    }

    @Test
    public void testThatListAuthorsSuccessfullyReturnsHttp200OK() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListAuthorsSuccessfullyReturnsListOfAuthors() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(null);
        String testAuthorAJson = objectMapper.writeValueAsString(testAuthorA);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testAuthorAJson)
        );

        AuthorEntity testAuthorB = TestDataUtil.createTestAuthorB();
        testAuthorA.setId(null);
        String testAuthorBJson = objectMapper.writeValueAsString(testAuthorB);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testAuthorBJson)
        );

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0]id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0]name").value(testAuthorA.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0]name").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0]age").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0]age").value(testAuthorA.getAge())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1]id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1]name").value(testAuthorB.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1]name").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1]age").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1]age").value(testAuthorB.getAge())
        );
    }

    @Test
    public void testThatGetAuthorSuccessfullyReturnsHttp302OKIfAuthorExists() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(null);
        AuthorEntity savedTestAuthor = authorService.saveAuthor(testAuthorA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + savedTestAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isFound()
        );
    }

    @Test
    public void testThatGetAuthorSuccessfullyReturnsHttp404IfAuthorNotExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + FAKE_AUTHOR_UD)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetAuthorSuccessfullyReturnsAuthorIfAuthorExists() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(null);
        AuthorEntity savedTestAuthor = authorService.saveAuthor(testAuthorA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + savedTestAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthorA.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").isNumber()
        );
    }

    @Test
    public void testThatFullUpdateAuthorSuccessfullyReturnsHttp404IfAuthorNotExists() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(FAKE_AUTHOR_UD);
        String testAuthorAJson = objectMapper.writeValueAsString(testAuthorA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + FAKE_AUTHOR_UD)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testAuthorAJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdateAuthorSuccessfullyReturnsHttp200IfAuthorExists() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(null);
        AuthorEntity savedTestAuthor = authorService.saveAuthor(testAuthorA);
        String testAuthorBJson = objectMapper.writeValueAsString(savedTestAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedTestAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testAuthorBJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateAuthorSuccessfullyReturnsUpdatedAuthor() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(null);
        AuthorEntity savedTestAuthor = authorService.saveAuthor(testAuthorA);

        AuthorEntity testAuthorB = TestDataUtil.createTestAuthorB();
        testAuthorA.setId(savedTestAuthor.getId());
        String testAuthorBJson = objectMapper.writeValueAsString(testAuthorB);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedTestAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testAuthorBJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthorB.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthorB.getAge())
        );
    }

    @Test
    public void testThatPartialUpdateAuthorSuccessfullyReturnsHttp404IfAuthorNotExists() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(FAKE_AUTHOR_UD);
        testAuthorA.setName("Updated");
        String testAuthorAJson = objectMapper.writeValueAsString(testAuthorA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/" + FAKE_AUTHOR_UD)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testAuthorAJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatPartialUpdateAuthorSuccessfullyReturnsHttp200IfAuthorExists() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(null);
        AuthorEntity savedTestAuthor = authorService.saveAuthor(testAuthorA);
        savedTestAuthor.setName("Updated");
        String testAuthorBJson = objectMapper.writeValueAsString(savedTestAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedTestAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testAuthorBJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateAuthorSuccessfullyReturnsUpdatedAuthor() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        testAuthorA.setId(null);
        AuthorEntity savedTestAuthor = authorService.saveAuthor(testAuthorA);

        testAuthorA.setId(savedTestAuthor.getId());
        testAuthorA.setName("Updated");
        String testAuthorBJson = objectMapper.writeValueAsString(testAuthorA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedTestAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testAuthorBJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthorA.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthorA.getAge())
        );
    }
}
