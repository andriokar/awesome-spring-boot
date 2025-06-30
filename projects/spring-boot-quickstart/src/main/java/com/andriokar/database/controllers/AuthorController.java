package com.andriokar.database.controllers;

import com.andriokar.database.domain.dto.AuthorDto;
import com.andriokar.database.domain.entities.AuthorEntity;
import com.andriokar.database.mappers.Mapper;
import com.andriokar.database.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AuthorController {

    @Autowired
    private final AuthorService authorService;

    @Autowired
    private final Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity savedAuthorEntity = authorService.createAuthor(authorEntity);

        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/authors")
    public ResponseEntity<List<AuthorDto>> getAuthors() {
        List<AuthorEntity> authors = authorService.findAll();

        return new ResponseEntity<>(authors.stream().map(authorMapper::mapTo).toList(), HttpStatus.OK);
    }

    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable(name = "id") Long id) {
        Optional<AuthorEntity> author = authorService.findOne(id);

        return author.map(authorEntity -> new ResponseEntity<>(
                        authorMapper.mapTo(authorEntity),
                        HttpStatus.FOUND)
                )
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
