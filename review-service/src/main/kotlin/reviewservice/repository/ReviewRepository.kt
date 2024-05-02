package reviewservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import reviewservice.model.Review
import java.util.*

interface ReviewRepository : JpaRepository<Review, UUID>{
    fun findByBookId(bookId: UUID): MutableList<Review>
    fun findByAccountId(userId: UUID): MutableList<Review>
    fun deleteAllByBookId(bookId: UUID)
    fun deleteAllByAccountId(userId: UUID)
}