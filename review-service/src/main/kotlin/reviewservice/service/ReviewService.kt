package reviewservice.service

import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.messaging.support.GenericMessage
import org.springframework.stereotype.Service
import reviewservice.client.AccountServiceClient
import reviewservice.client.BookServiceClient
import reviewservice.config.KafkaProducer
import reviewservice.config.ReviewCreatedTopicProperties
import reviewservice.dto.ReviewCreatedPayload
import reviewservice.dto.ReviewRequest
import reviewservice.dto.ReviewResponse
import reviewservice.exception.ReviewNotFoundException
import reviewservice.model.Review
import reviewservice.repository.ReviewRepository
import reviewservice.utils.KafkaConstants
import java.util.*

@Service
class ReviewService(val reviewRepository: ReviewRepository,
                    val bookServiceClient: BookServiceClient,
                    val accountServiceClient: AccountServiceClient,
                    val kafkaProducer: KafkaProducer,
                    val reviewCreatedTopicProperties: ReviewCreatedTopicProperties) {


    private val logger = LoggerFactory.getLogger(ReviewService::class.java)
    fun getAllReviews(): MutableList<ReviewResponse> {
        logger.info("Fetching all reviews")
        return reviewRepository.findAll()
            .map { ReviewResponse.toReviewResponse(it) }
            .toMutableList()
    }

    fun getReviewById(id: UUID): ReviewResponse{
        logger.info("Fetching review with id: $id")
        return findById(id)
            .let { ReviewResponse.toReviewResponse(it) }
    }

    fun getReviewsByBookId(bookId: UUID): MutableList<ReviewResponse> {
        logger.info("Fetching reviews for book with id: $bookId")
        bookServiceClient.getBookById(bookId) // Check if book exists
        return reviewRepository.findByBookId(bookId)
            .map { ReviewResponse.toReviewResponse(it) }
            .toMutableList()
    }

    fun getReviewsByAccountId(userId: UUID): MutableList<ReviewResponse> {
        logger.info("Fetching reviews for account with id: $userId")
        accountServiceClient.getAccountById(userId) // Check if account exists
        return reviewRepository.findByAccountId(userId)
            .map { ReviewResponse.toReviewResponse(it) }
            .toMutableList()
    }

    fun saveReview(review: ReviewRequest) : ReviewResponse {
        logger.info("Saving review : $review")
        bookServiceClient.getBookById(review.bookId) // Check if book exists
        accountServiceClient.getAccountById(review.accountId) // Check if account exists
        val savedReview = reviewRepository.save(
            Review( accountId = review.accountId, bookId = review.bookId, rating = review.rating, review = review.review))
        // send message to consumer
        val payload = ReviewCreatedPayload.toReviewCreatedPayload(savedReview)
        val headers = mutableMapOf<String, Any>(
            KafkaConstants.TOPIC to reviewCreatedTopicProperties.topicName,
            KafkaConstants.MESSAGE_KEY to savedReview.id.toString()
        )
        kafkaProducer.sendMessage(GenericMessage(payload, headers))

        return ReviewResponse.toReviewResponse(savedReview)
    }

    fun updateReview(id: UUID, review: ReviewRequest) : ReviewResponse {
        logger.info("Updating review with id: $id")
        val existingReview = findById(id)
        existingReview.rating = review.rating
        existingReview.review = review.review
        reviewRepository.save(existingReview)
        return ReviewResponse.toReviewResponse(existingReview)
    }

    fun deleteReview(id: UUID) {
        logger.info("Deleting review with id: $id")
        val existingReview = findById(id)
        reviewRepository.delete(existingReview)
    }

    @Transactional
    fun deleteReviewsByBookId(bookId: UUID) {
        logger.info("Deleting reviews for book with id: $bookId")
        bookServiceClient.getBookById(bookId) // Check if book exists
        reviewRepository.deleteAllByBookId(bookId)
    }

    @Transactional
    fun deleteReviewsByAccountId(userId: UUID) {
        logger.info("Deleting reviews for account with id: $userId")
        accountServiceClient.getAccountById(userId) // Check if account exists
        reviewRepository.deleteAllByAccountId(userId)
    }
    fun findById(id: UUID): Review {
        return reviewRepository.findById(id).orElseThrow { throw ReviewNotFoundException("Review not found with id: $id") }
    }
}