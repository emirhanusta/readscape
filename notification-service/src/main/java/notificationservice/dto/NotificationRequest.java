package notificationservice.dto;

import notificationservice.consumers.model.ReviewCreatedPayload;

import java.util.UUID;

public record NotificationRequest(
        UUID accountId,
        String message
) {
    public static NotificationRequest from(ReviewCreatedPayload reviewCreatedPayload) {
        return new NotificationRequest(
                reviewCreatedPayload.getAccountId(),
                "Review created for book " + reviewCreatedPayload.getBookId()
        );
    }
}
