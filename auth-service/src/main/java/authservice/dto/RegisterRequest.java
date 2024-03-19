package authservice.dto;

public record RegisterRequest(
        String username,
        String password,
        String role
) {
}

