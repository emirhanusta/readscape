package authorservice.service

import authorservice.client.BookServiceClient
import authorservice.dto.AuthorRequest
import authorservice.dto.AuthorResponse
import authorservice.dto.BookResponse
import authorservice.exception.AuthorNotFoundException
import authorservice.model.Author
import authorservice.repository.AuthorRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AuthorService(val authorRepository: AuthorRepository, val bookServiceClient: BookServiceClient) {

    fun getAll(): MutableList<AuthorResponse> = authorRepository.findAll()
        .map { AuthorResponse.toAuthorResponse(it) }
        .toMutableList()

    fun getById(id: UUID) : AuthorResponse = AuthorResponse.toAuthorResponse(findById(id))

    fun getBooksByAuthor(id: UUID) : ResponseEntity<List<BookResponse>> = bookServiceClient.getBooksByAuthor(id)

    fun saveAuthor(request: AuthorRequest) : AuthorResponse {
        val savedAuthor = Author(
             name = request.name, biography = request.biography, birthDate = request.birthDate
        )
        return AuthorResponse.toAuthorResponse(authorRepository.save(savedAuthor))
    }

    fun updateAuthor(id: UUID, request: AuthorRequest) : AuthorResponse {
        val existingAuthor = findById(id)
        existingAuthor.name = request.name
        existingAuthor.biography = request.biography
        existingAuthor.birthDate = request.birthDate
        authorRepository.save(existingAuthor)
        return AuthorResponse.toAuthorResponse(existingAuthor)
    }

    fun deleteAuthor(id: UUID) {
        val existingAuthor = findById(id)
        authorRepository.delete(existingAuthor)
    }
    fun findById(id: UUID): Author = authorRepository.findById(id)
        .orElseThrow { throw AuthorNotFoundException("Author not found with id: $id") }

}