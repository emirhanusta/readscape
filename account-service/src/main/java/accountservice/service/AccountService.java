package accountservice.service;

import accountservice.dto.AccountRequest;
import accountservice.dto.AccountResponse;
import accountservice.exception.AccountNotFoundException;
import accountservice.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import accountservice.repository.AccountRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountResponse createAccount(AccountRequest accountRequest) {
        Account account = Account.builder()
                .username(accountRequest.username())
                .email(accountRequest.email())
                .build();
        accountRepository.save(account);
        return AccountResponse.from(account);
    }

    public AccountResponse getAccountById(UUID id) {
        return AccountResponse.from(getAccount(id));
    }

    public AccountResponse updateAccount(UUID id, AccountRequest accountRequest) {
        Account account = getAccount(id);
        account.setUsername(accountRequest.username());
        account.setEmail(accountRequest.email());
        accountRepository.save(account);
        return AccountResponse.from(account);
    }

    public void deleteAccount(UUID id) {
        accountRepository.delete(getAccount(id));
    }

    private Account getAccount(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));
    }
}
