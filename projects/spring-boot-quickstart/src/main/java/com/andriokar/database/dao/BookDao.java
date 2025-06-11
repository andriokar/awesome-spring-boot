package com.andriokar.database.dao;

import com.andriokar.database.domain.Book;

import java.util.Optional;

public interface BookDao {

    void create(Book book);

    Optional<Book> find(String isbn);
}
