package authservice.dto;


import java.util.UUID;

public record AccountClientResponse(
        UUID id,
        String username,
        String password,
        String email,
        String role
) {
}
