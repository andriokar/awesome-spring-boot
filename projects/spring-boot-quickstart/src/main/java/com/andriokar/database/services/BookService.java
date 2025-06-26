package com.andriokar.database.services;

import com.andriokar.database.domain.entities.BookEntity;

public interface BookService {

    BookEntity createBook(String isbn, BookEntity bookEntity);
}
