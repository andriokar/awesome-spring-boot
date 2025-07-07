package com.andriokar.database.controllers;

import com.andriokar.database.domain.dto.BookDto;
import com.andriokar.database.domain.entities.BookEntity;
import com.andriokar.database.mappers.Mapper;
import com.andriokar.database.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<BookDto> createUpdateBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto
    ) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        boolean bookExists = bookService.isExists(isbn);
        BookEntity createdBook = bookService.createUpdateBook(isbn, bookEntity);
        BookDto savedUpdatedBookDto = bookMapper.mapTo(createdBook);

        if (bookExists) {
            return new ResponseEntity<>(savedUpdatedBookDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(savedUpdatedBookDto, HttpStatus.CREATED);
        }
    }

    @GetMapping(path = "/books")
    public ResponseEntity<Page<BookDto>> listBooks(
            Pageable pageable
    ) {
        Page<BookEntity> books = bookService.findAll(pageable);

        return new ResponseEntity<>(books.map(bookMapper::mapTo), HttpStatus.OK);
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable(name = "isbn") String isbn) {
        Optional<BookEntity> book = bookService.findOne(isbn);

        return book.map(authorEntity -> new ResponseEntity<>(
                        bookMapper.mapTo(authorEntity),
                        HttpStatus.FOUND)
                )
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto
    ) {
        if (!bookService.isExists(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity updatedBook = bookService.partialUpdate(isbn, bookEntity);

        return new ResponseEntity<>(bookMapper.mapTo(updatedBook), HttpStatus.OK);
    }

    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity deleteBook(
            @PathVariable("isbn") String isbn
    ) {
        bookService.delete(isbn);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
