package com.andriokar.database.controllers;

import com.andriokar.database.TestDataUtil;
import com.andriokar.database.domain.dto.BookDto;
import com.andriokar.database.domain.entities.BookEntity;
import com.andriokar.database.services.BookService;
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
public class BookControllerIntegrationTests {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    private final BookService bookService;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, BookService bookService) {
        this.mockMvc = mockMvc;
        this.bookService = bookService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnsHttp201Created() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String createBookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnsSavedBook() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String createBookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").isString()
        );
    }

    @Test
    public void testThatListBooksSuccessfullyReturnsHttp200OK() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListBooksSuccessfullyReturnsListOfBooks() throws Exception {
        BookDto bookDtoA = TestDataUtil.createTestBookDtoA(null);
        BookDto bookDtoB = TestDataUtil.createTestBookDtoB(null);

        String createBookJsonA = objectMapper.writeValueAsString(bookDtoA);
        String createBookJsonB = objectMapper.writeValueAsString(bookDtoB);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDtoA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBookJsonA)
        );

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDtoB.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBookJsonB)
        );

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0]isbn").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0]isbn").value(bookDtoA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0]title").value(bookDtoA.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0]title").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1]isbn").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1]isbn").value(bookDtoB.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1]title").value(bookDtoB.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1]title").isString()
        );
    }

    @Test
    public void testThatGetBookSuccessfullyReturns302OKIfBookExists() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA(null);
        bookService.createBook(bookEntity.getIsbn(), bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + bookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isFound()
        );
    }

    @Test
    public void testThatGetBookSuccessfullyReturns404IfBookNotExists() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA(null);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + bookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetBookSuccessfullyReturnsBookIfBookExists() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA(null);
        bookService.createBook(bookEntity.getIsbn(), bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + bookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookEntity.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").isString()
        );
    }
}
