package authservice.service;

import authservice.client.AccountServiceClient;
import authservice.dto.AccountClientResponse;
import authservice.dto.RegisterRequest;
import authservice.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AccountServiceClient accountServiceClient;

    protected UserResponse saveUser(RegisterRequest register) {
        return accountServiceClient.createAccount(register).getBody();
    }

    protected UserResponse findByUsername(String username) {
        AccountClientResponse account = accountServiceClient.getByUsername(username).getBody();
        return UserResponse.builder()
                .id(Objects.requireNonNull(account).id())
                .username(username)
                .email(Objects.requireNonNull(account.email()))
                .role(Objects.requireNonNull(account.role()))
                .build();
    }

    public boolean existsByUsername(String username) {
        return Boolean.TRUE.equals(accountServiceClient.isExist(username).getBody());
    }
}
