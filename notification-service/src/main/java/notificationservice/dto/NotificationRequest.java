package notificationservice.dto;

import notificationservice.consumers.model.ReviewCreatedPayload;

import java.util.UUID;

public record NotificationRequest(
        UUID accountId,
        UUID reviewId,
        String message
) {
    public static NotificationRequest from(ReviewCreatedPayload reviewCreatedPayload) {
        return new NotificationRequest(
                reviewCreatedPayload.getAccountId(),
                reviewCreatedPayload.getId(),
                "Review created for book " + reviewCreatedPayload.getBookId()
        );
    }
}
