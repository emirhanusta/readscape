package com.bookservice.dto;

import com.bookservice.model.Book;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record BookResponse (
        UUID id,
        UUID authorId,
        String title,
        String description,
        List<String> genres,
        @JsonSerialize(using = LocalDateSerializer.class)
        @JsonDeserialize(using = LocalDateDeserializer.class)
        LocalDate publishedDate
)implements Serializable{
    public static BookResponse toDto(Book book){
        return new BookResponse(
                book.getId(),
                book.getAuthorId(),
                book.getTitle(),
                book.getDescription(),
                List.of(book.getGenres().toString()),
                book.getPublishedDate()
        );
    }
}
