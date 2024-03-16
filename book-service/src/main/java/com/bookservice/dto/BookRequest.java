package com.bookservice.dto;

import com.bookservice.model.enums.BookGenres;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record BookRequest(
        UUID authorId,
        String title,
        String isbn,
        String description,
        List<BookGenres> genres,
        Date publishedDate
) {
}
