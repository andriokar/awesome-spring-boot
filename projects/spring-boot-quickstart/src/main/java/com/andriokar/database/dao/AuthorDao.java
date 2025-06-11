package com.andriokar.database.dao;

import com.andriokar.database.domain.Author;

import java.util.Optional;

public interface AuthorDao {

    void create(Author author);

    Optional<Author> findOne(long l);
}
