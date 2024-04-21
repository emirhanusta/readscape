package accountservice.dto;

public record AccountRequest(
        String username,
        String email
) {
}
