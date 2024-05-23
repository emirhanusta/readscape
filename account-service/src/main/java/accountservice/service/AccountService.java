package accountservice.service;

import accountservice.client.ReviewServiceClient;
import accountservice.dto.AccountClientResponse;
import accountservice.dto.AccountRequest;
import accountservice.dto.AccountResponse;
import accountservice.dto.ReviewResponse;
import accountservice.exception.AccountNotFoundException;
import accountservice.model.Account;
import accountservice.model.enums.Role;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import accountservice.repository.AccountRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    Logger logger = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository accountRepository;
    private final ReviewServiceClient reviewServiceClient;

    public AccountResponse createAccount(AccountRequest accountRequest) {
        logger.info("Creating account : {}", accountRequest);
        Account account = Account.builder()
                .username(accountRequest.username())
                .password(accountRequest.password())
                .email(accountRequest.email())
                .role(Role.valueOf(accountRequest.role()))
                .build();
        accountRepository.save(account);
        return AccountResponse.from(account);
    }

    public AccountResponse getAccountById(UUID id) {
        logger.info("Getting account by id : {}", id);
        return AccountResponse.from(getAccount(id));
    }

    public AccountClientResponse getAccountByUsername(String username) {
        logger.info("Getting account by username : {}", username);
        return AccountClientResponse.from(accountRepository.findByUsername(username)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with username: " + username)));
    }

    public List<ReviewResponse> getReviewsByAccountId(UUID accountId) {
        logger.info("Getting reviews by account id : {}", accountId);
        return reviewServiceClient.getReviewsByAccountId(accountId).getBody();
    }
    public AccountResponse updateAccount(UUID id, AccountRequest accountRequest) {
        logger.info("Updating account with id : {} and request : {}", id, accountRequest);
        Account account = getAccount(id);
        account.setUsername(accountRequest.username());
        account.setEmail(accountRequest.email());
        accountRepository.save(account);
        return AccountResponse.from(account);
    }

    public void deleteAccount(UUID id) {
        logger.info("Deleting account with id : {}", id);
        reviewServiceClient.deleteReviewsByAccountId(id);
        accountRepository.delete(getAccount(id));
    }

    public boolean existsByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }

    private Account getAccount(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));
    }
}
