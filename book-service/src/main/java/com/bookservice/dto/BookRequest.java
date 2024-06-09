package com.bookservice.dto;

import com.bookservice.model.enums.BookGenres;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record BookRequest(
        UUID authorId,
        String title,
        String description,
        List<BookGenres> genres,
        LocalDate publishedDate
) {
}
