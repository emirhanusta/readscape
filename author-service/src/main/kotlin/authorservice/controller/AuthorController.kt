package authorservice.controller

import authorservice.dto.AuthorRequest
import authorservice.dto.AuthorResponse
import authorservice.dto.BookResponse
import authorservice.service.AuthorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/authors")
class AuthorController(val authorService: AuthorService) {

    @GetMapping
    fun getAllAuthors() : ResponseEntity<MutableList<AuthorResponse>> =
        ResponseEntity.ok(authorService.getAll())

    @GetMapping("/{id}")
    fun getAuthorById(@PathVariable id: UUID) : ResponseEntity<AuthorResponse> =
        ResponseEntity.ok(authorService.getById(id))

    @GetMapping("/{id}/books")
    fun getBooksByAuthor(@PathVariable id: UUID) : ResponseEntity<List<BookResponse>> =
        authorService.getBooksByAuthor(id)

    @PostMapping
    fun saveAuthor(@RequestBody author: AuthorRequest) : ResponseEntity<AuthorResponse> =
        ResponseEntity.ok(authorService.saveAuthor(author))

    @PutMapping("/{id}")
    fun updateAuthor(@PathVariable id: UUID,@RequestBody author: AuthorRequest) : ResponseEntity<AuthorResponse> =
        ResponseEntity.ok(authorService.updateAuthor(id, author))

    @DeleteMapping("/{id}")
    fun deleteAuthor(@PathVariable id: UUID) = authorService.deleteAuthor(id)

}