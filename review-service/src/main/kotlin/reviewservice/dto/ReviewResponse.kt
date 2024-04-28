package reviewservice.dto

import reviewservice.model.Review
import java.util.*

data class ReviewResponse(
    val id: UUID,
    val accountId: UUID,
    val bookId: UUID,
    val rating: Float,
    val review: String
){
    companion object {
        fun toReviewResponse(review: Review): ReviewResponse {
            return ReviewResponse(review.id!!, review.accountId, review.bookId, review.rating, review.review)
        }
    }
}
