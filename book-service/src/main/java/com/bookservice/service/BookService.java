package com.bookservice.service;

import com.bookservice.dto.BookRequest;
import com.bookservice.dto.BookResponse;
import com.bookservice.exception.BookNotFoundException;
import com.bookservice.model.Book;
import com.bookservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final S3Service s3Service;

    public BookResponse addNewBook(BookRequest book) {
        Book newBook = Book.builder()
                .authorId(book.authorId())
                .title(book.title())
                .isbn(book.isbn())
                .description(book.description())
                .genres(book.genres())
                .publishedDate(book.publishedDate())
                .build();
        bookRepository.save(newBook);
        return BookResponse.toDto(newBook);
    }
    public BookResponse getBookById(UUID id) {
        return BookResponse.toDto(findBookById(id));
    }
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookResponse::toDto)
                .toList();
    }
    public BookResponse updateBookById(UUID id, BookRequest book) {
        Book existingBook = findBookById(id);
        existingBook.setAuthorId(book.authorId());
        existingBook.setTitle(book.title());
        existingBook.setIsbn(book.isbn());
        existingBook.setDescription(book.description());
        existingBook.setGenres(book.genres());
        existingBook.setPublishedDate(book.publishedDate());
        bookRepository.save(existingBook);
        return BookResponse.toDto(existingBook);
    }
    public void deleteBookById(UUID id) {
        findBookById(id); // check if book exists (throws exception if not found)
        s3Service.deleteImage(id);
        bookRepository.deleteById(id);
    }

    private Book findBookById(UUID id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new BookNotFoundException("Book not found with id: " + id)
        );
    }
}
