package accountservice.client;

import accountservice.dto.ReviewResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "review-service", path = "/api/v1/reviews")
public interface ReviewServiceClient {

    @GetMapping("/account/{accountId}")
    ResponseEntity<List<ReviewResponse>> getReviewsByAccountId(@PathVariable UUID accountId);

    @DeleteMapping("/account/{accountId}")
    ResponseEntity<Void> deleteReviewsByAccountId(@PathVariable UUID accountId);
}
