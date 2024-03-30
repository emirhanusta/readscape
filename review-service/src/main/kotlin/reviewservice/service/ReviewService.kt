package reviewservice.service

import org.springframework.stereotype.Service
import reviewservice.dto.ReviewRequest
import reviewservice.dto.ReviewResponse
import reviewservice.exception.ReviewNotFoundException
import reviewservice.model.Review
import reviewservice.repository.ReviewRepository
import java.util.*

@Service
class ReviewService(val reviewRepository: ReviewRepository) {

    fun getAllReviews(): MutableList<ReviewResponse> = reviewRepository.findAll()
        .map { ReviewResponse.toReviewResponse(it) }
        .toMutableList()

    fun getReviewById(id: UUID) = findById(id)
        .let { ReviewResponse.toReviewResponse(it) }

    fun saveReview(review: ReviewRequest) : ReviewResponse {
        val savedReview = reviewRepository.save(
            Review( userId = review.userId, bookId = review.bookId, rating = review.rating, review = review.review))
        return ReviewResponse.toReviewResponse(savedReview)
    }

    fun updateReview(id: UUID, review: ReviewRequest) : ReviewResponse {
        val existingReview = findById(id)
        existingReview.userId = review.userId
        existingReview.bookId = review.bookId
        existingReview.rating = review.rating
        existingReview.review = review.review
        reviewRepository.save(existingReview)
        return ReviewResponse.toReviewResponse(existingReview)
    }

    fun deleteReview(id: UUID) {
        val existingReview = findById(id)
        reviewRepository.delete(existingReview)
    }

    fun findById(id: UUID): Review {
        return reviewRepository.findById(id).orElseThrow { throw ReviewNotFoundException("Review not found with id: $id") }
    }
}