package reviewservice.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reviewservice.dto.ReviewRequest
import reviewservice.dto.ReviewResponse
import reviewservice.service.ReviewService
import java.util.UUID

@RestController
@RequestMapping("/api/v1/reviews")
class ReviewController(val reviewService: ReviewService) {

    @GetMapping
    fun getAllReviews(): ResponseEntity<MutableList<ReviewResponse>> = ResponseEntity.ok(reviewService.getAllReviews())

    @GetMapping("/{id}")
    fun getReviewById(@PathVariable id: UUID): ResponseEntity<ReviewResponse> = ResponseEntity.ok(reviewService.getReviewById(id))

    @PostMapping
    fun saveReview(@RequestBody review: ReviewRequest): ResponseEntity<ReviewResponse> = ResponseEntity.ok(reviewService.saveReview(review))

    @PutMapping("/{id}")
    fun updateReview(@PathVariable id: UUID, @RequestBody review: ReviewRequest): ResponseEntity<ReviewResponse> = ResponseEntity.ok(reviewService.updateReview(id, review))

    @DeleteMapping("/{id}")
    fun deleteReview(@PathVariable id: UUID) = reviewService.deleteReview(id)
}