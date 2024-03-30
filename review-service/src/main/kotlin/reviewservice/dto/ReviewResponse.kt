package reviewservice.dto

import reviewservice.model.Review
import java.util.*

data class ReviewResponse(
    val id: UUID,
    val userId: UUID,
    val bookId: UUID,
    val rating: Float,
    val review: String
){
    companion object {
        fun toReviewResponse(review: Review): ReviewResponse {
            return ReviewResponse(review.id!!, review.userId, review.bookId, review.rating, review.review)
        }
    }
}
