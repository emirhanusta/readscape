package authorservice.repository

import authorservice.model.Author
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AuthorRepository : JpaRepository<Author, UUID>{
}