package accountservice.dto;

public record AccountRequest(
        String username,
        String password,
        String email,
        String role
) {
}
