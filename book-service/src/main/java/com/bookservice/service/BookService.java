package com.bookservice.service;

import com.bookservice.client.AuthorServiceClient;
import com.bookservice.client.ReviewServiceClient;
import com.bookservice.dto.BookRequest;
import com.bookservice.dto.BookResponse;
import com.bookservice.dto.ReviewResponse;
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
    private final AuthorServiceClient authorServiceClient;
    private final ReviewServiceClient reviewServiceClient;

    public BookResponse addNewBook(BookRequest book) {
        authorServiceClient.getAuthorById(book.authorId()); // check if author exists (throws exception if not found)
        Book newBook = Book.builder()
                .authorId(book.authorId())
                .title(book.title())
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

    public List<BookResponse> getBooksByAuthorId(UUID authorId) {
        authorServiceClient.getAuthorById(authorId); // check if author exists (throws exception if not found)
        return bookRepository.findByAuthorId(authorId)
                .stream()
                .map(BookResponse::toDto)
                .toList();
    }

    public List<ReviewResponse> getReviewsByBookId(UUID bookId) {
        return reviewServiceClient.getReviewsByBookId(bookId).getBody();
    }

    public BookResponse updateBookById(UUID id, BookRequest book) {
        Book existingBook = findBookById(id);
        authorServiceClient.getAuthorById(book.authorId()); // check if author exists (throws exception if not found)
        existingBook.setAuthorId(book.authorId());
        existingBook.setTitle(book.title());
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
