package authservice.dto;

import lombok.Builder;

@Builder
public record TokenResponse(
        UserResponse user,
        String token
) {
}