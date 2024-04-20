package accountservice.dto;

import accountservice.model.Account;

import java.util.UUID;

public record AccountResponse(
        UUID id,
        String username,
        String email,
        String role
) {
    public static AccountResponse from(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getUsername(),
                account.getEmail(),
                account.getRole().name()
        );
    }
}