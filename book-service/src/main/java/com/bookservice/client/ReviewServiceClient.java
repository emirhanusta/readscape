package com.bookservice.client;

import com.bookservice.dto.ReviewResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "review-service", path = "/api/v1/reviews")
public interface ReviewServiceClient {

    @GetMapping("/book/{bookId}")
    ResponseEntity<List<ReviewResponse>> getReviewsByBookId(@PathVariable UUID bookId);

    @DeleteMapping("/book/{bookId}")
    ResponseEntity<Void> deleteReviewsByBookId(@PathVariable UUID bookId);
}
