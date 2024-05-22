package notificationservice.consumers.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewCreatedPayload {
    private UUID id;
    private UUID accountId;
    private UUID bookId;
    private String review;
    private Float rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
