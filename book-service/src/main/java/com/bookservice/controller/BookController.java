package com.bookservice.controller;

import com.bookservice.dto.BookRequest;
import com.bookservice.dto.BookResponse;
import com.bookservice.dto.ReviewResponse;
import com.bookservice.service.BookService;
import com.bookservice.service.S3Service;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;
    private final S3Service s3Service;

    public BookController(BookService bookService, S3Service s3Service) {
        this.bookService = bookService;
        this.s3Service = s3Service;
    }

    @PostMapping("/images/{id}")
    public ResponseEntity<InputStreamResource> saveImage(@PathVariable UUID id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .header("Content-Disposition", "attachment; filename=" + id.toString() + ".png")
                .body(s3Service.saveImage(id, file));
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable UUID id) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .header("Content-Disposition", "attachment; filename=" + id.toString() + ".png")
                .body(s3Service.viewImage(id));
    }

    @DeleteMapping("/images/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable UUID id) {
        s3Service.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
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

    @GetMapping("/reviews/{bookId}")
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
