package accountservice.dto;

import accountservice.model.Account;

import java.util.UUID;

public record AccountClientResponse(
        UUID id,
        String username,
        String password,
        String email,
        String role
) {
    public static AccountClientResponse from(Account account) {
        return new AccountClientResponse(
                account.getId(),
                account.getUsername(),
                account.getPassword(),
                account.getEmail(),
                account.getRole().toString()
        );
    }
}
