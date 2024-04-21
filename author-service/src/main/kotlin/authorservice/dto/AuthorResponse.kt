package authorservice.dto

import authorservice.model.Author
import java.time.LocalDate
import java.util.*

data class AuthorResponse (
    val id: UUID,
    val name: String,
    val biography: String,
    val birthDate: LocalDate
){
    companion object{
        fun toAuthorResponse(author: Author): AuthorResponse{
            return AuthorResponse(
                author.id!!,
                author.name,
                author.biography,
                author.birthDate
            )
        }
    }
}
