package com.andriokar.database.mappers.impl;

import com.andriokar.database.domain.dto.BookDto;
import com.andriokar.database.domain.entities.BookEntity;
import com.andriokar.database.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements Mapper<BookEntity, BookDto> {

    @Autowired
    private final ModelMapper modelMapper;

    public BookMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDto mapTo(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDto.class);
    }

    @Override
    public BookEntity mapFrom(BookDto bookDto) {
        return modelMapper.map(bookDto, BookEntity.class);
    }
}
