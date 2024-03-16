package com.bookservice.model;

import com.bookservice.model.enums.BookGenres;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Nonnull
    private String title;
    @Nonnull
    private UUID authorId;
    @Nonnull
    private String isbn;
    private String description;
    @ElementCollection(targetClass = BookGenres.class)
    @Enumerated(EnumType.STRING)
    private List<BookGenres> genres;
    private Date publishedDate;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
}