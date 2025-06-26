package com.andriokar.database.controllers;

import com.andriokar.database.domain.dto.BookDto;
import com.andriokar.database.domain.entities.BookEntity;
import com.andriokar.database.mappers.Mapper;
import com.andriokar.database.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @Autowired
    private final Mapper<BookEntity, BookDto> bookMapper;

    @Autowired
    private final BookService bookService;

    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto
    ) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity createdBook = bookService.createBook(isbn, bookEntity);

        return new ResponseEntity<>(bookMapper.mapTo(createdBook), HttpStatus.CREATED);
    }
}
