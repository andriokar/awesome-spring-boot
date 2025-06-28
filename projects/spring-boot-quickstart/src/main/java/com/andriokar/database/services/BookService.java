package com.andriokar.database.services;

import com.andriokar.database.domain.entities.BookEntity;

import java.util.List;

public interface BookService {

    BookEntity createBook(String isbn, BookEntity bookEntity);

    List<BookEntity> findAll();
}
