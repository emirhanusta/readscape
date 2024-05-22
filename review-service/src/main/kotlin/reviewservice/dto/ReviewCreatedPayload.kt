package reviewservice.dto

import reviewservice.model.Review
import java.time.LocalDateTime
import java.util.*

data class ReviewCreatedPayload(
    val id: String,
    val accountId: UUID,
    val bookId: UUID,
    var rating: Float,
    var review: String,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
){
    companion object {
        fun toReviewCreatedPayload(review: Review) : ReviewCreatedPayload {
            return ReviewCreatedPayload(
                id = review.id.toString(),
                accountId = review.accountId,
                bookId = review.bookId,
                rating = review.rating,
                review = review.review,
                createdAt = review.createdAt,
                updatedAt = review.updatedAt
            )
        }
    }
}
