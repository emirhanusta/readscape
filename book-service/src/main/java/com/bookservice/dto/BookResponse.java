package com.bookservice.dto;

import com.bookservice.model.Book;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record BookResponse(
        UUID id,
        UUID authorId,
        String title,
        String isbn,
        String description,
        List<String> genres,
        Date publishedDate
){
    public static BookResponse toDto(Book book){
        return new BookResponse(
                book.getId(),
                book.getAuthorId(),
                book.getTitle(),
                book.getIsbn(),
                book.getDescription(),
                List.of(book.getGenres().toString()),
                book.getPublishedDate()
        );
    }
}
