package notificationservice.consumers.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewCreatedEvent {
    private UUID id;
    private UUID accountId;
    private UUID bookId;
    private String review;
    private float rating;
    private boolean status;
}
