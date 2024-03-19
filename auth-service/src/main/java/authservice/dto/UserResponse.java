package authservice.dto;

import authservice.model.User;
import lombok.Builder;


import java.util.UUID;

@Builder
public record UserResponse(
        UUID id,
        String username,
        String role
) {
    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }
}

