package com.andriokar.database.services.impl;

import com.andriokar.database.domain.entities.BookEntity;
import com.andriokar.database.repositories.BookRepository;
import com.andriokar.database.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);

        return bookRepository.save(bookEntity);
    }

    @Override
    public List<BookEntity> findAll() {
        return StreamSupport.stream(
                        bookRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }
}
