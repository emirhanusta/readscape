package com.bookservice.controller;

import com.bookservice.dto.BookRequest;
import com.bookservice.dto.BookResponse;
import com.bookservice.dto.ReviewResponse;
import com.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> addNewBook(@RequestBody BookRequest book) {
        return ResponseEntity.ok(bookService.addNewBook(book));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable UUID id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<BookResponse>> getBooksByAuthorId(@PathVariable UUID authorId) {
        return ResponseEntity.ok(bookService.getBooksByAuthorId(authorId));
    }

    @GetMapping("/{bookId}/reviews")
    public ResponseEntity<List<ReviewResponse>> getReviewsByBookId(@PathVariable UUID bookId) {
        return ResponseEntity.ok(bookService.getReviewsByBookId(bookId));
    }
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBookById(@PathVariable UUID id, @RequestBody BookRequest book) {
        return ResponseEntity.ok(bookService.updateBookById(id, book));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable UUID id) {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/author/{authorId}")
    public ResponseEntity<Void> deleteBooksByAuthorId(@PathVariable UUID authorId) {
        bookService.deleteBooksByAuthorId(authorId);
        return ResponseEntity.noContent().build();
    }
}
