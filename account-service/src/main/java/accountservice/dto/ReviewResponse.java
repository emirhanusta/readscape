package accountservice.dto;

import java.util.UUID;

public record ReviewResponse(
        UUID id,
        UUID bookId,
        UUID accountId,
        float rating,
        String review
) {
}
