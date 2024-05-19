package notificationservice.dto;

import notificationservice.consumers.model.ReviewCreatedEvent;

import java.util.UUID;

public record NotificationRequest(
        UUID accountId,
        UUID reviewId,
        String message
) {
    public static NotificationRequest from(ReviewCreatedEvent reviewCreatedEvent) {
        return new NotificationRequest(
                reviewCreatedEvent.getAccountId(),
                reviewCreatedEvent.getId(),
                "Review created"
        );
    }
}
