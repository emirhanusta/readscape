package notificationservice.consumers.model;

import lombok.*;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewCreatedEvent {
    private ReviewCreatedPayload payload;
    private Map<String, Object> headers;
}
