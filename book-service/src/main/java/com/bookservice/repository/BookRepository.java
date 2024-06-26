package com.bookservice.repository;

import com.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByAuthorId(UUID authorId);

    void deleteByAuthorId(UUID authorId);
}
