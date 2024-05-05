package authorservice.service

import authorservice.client.BookServiceClient
import authorservice.dto.AuthorRequest
import authorservice.dto.AuthorResponse
import authorservice.dto.BookResponse
import authorservice.exception.AuthorNotFoundException
import authorservice.model.Author
import authorservice.repository.AuthorRepository
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AuthorService(val authorRepository: AuthorRepository, val bookServiceClient: BookServiceClient) {

    private val log = LoggerFactory.getLogger(AuthorService::class.java)
    fun getAll(): MutableList<AuthorResponse> {
        log.info("Fetching all authors")
        return  authorRepository.findAll()
            .map { AuthorResponse.toAuthorResponse(it) }
            .toMutableList()
    }
    fun getById(id: UUID) : AuthorResponse {
        log.info("Fetching author by id: $id")
        return AuthorResponse.toAuthorResponse(findById(id))
    }

    fun getBooksByAuthor(id: UUID) : ResponseEntity<List<BookResponse>> {
        log.info("Fetching books by author id: $id")
        return bookServiceClient.getBooksByAuthor(id)
    }

    fun saveAuthor(request: AuthorRequest) : AuthorResponse {
        log.info("Saving author : $request")
        val savedAuthor = Author(
             name = request.name, biography = request.biography, birthDate = request.birthDate
        )
        return AuthorResponse.toAuthorResponse(authorRepository.save(savedAuthor))
    }

    fun updateAuthor(id: UUID, request: AuthorRequest) : AuthorResponse {
        log.info("Updating author with id: $id")
        val existingAuthor = findById(id)
        existingAuthor.name = request.name
        existingAuthor.biography = request.biography
        existingAuthor.birthDate = request.birthDate
        authorRepository.save(existingAuthor)
        return AuthorResponse.toAuthorResponse(existingAuthor)
    }

    fun deleteAuthor(id: UUID) {
        log.info("Deleting author with id: $id")
        bookServiceClient.deleteBooksByAuthorId(id) // Delete books by author
        val existingAuthor = findById(id)
        authorRepository.delete(existingAuthor)
    }
    fun findById(id: UUID): Author = authorRepository.findById(id)
        .orElseThrow { throw AuthorNotFoundException("Author not found with id: $id") }

}