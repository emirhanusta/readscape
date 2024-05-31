package com.bookservice.model

import com.bookservice.model.enums.BookGenres
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Entity
data class Book @JvmOverloads constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    var title: String,
    var authorId: UUID,
    var description: String,
    @ElementCollection(targetClass = BookGenres::class)
    @Enumerated(EnumType.STRING)
    var genres: List<BookGenres>,
    var publishedDate: LocalDate,
    @CreationTimestamp
    val createdAt: LocalDateTime? = null,
    @UpdateTimestamp
    val updatedAt: LocalDateTime? = null
)