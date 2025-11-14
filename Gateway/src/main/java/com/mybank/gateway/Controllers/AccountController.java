package com.mybank.gateway.Controllers;

import com.mybank.gateway.DTO.AccountDTO;
import com.mybank.gateway.DTO.TransactionDTO;
import com.mybank.gateway.DTO.UserDTO;
import com.mybank.gateway.Services.AccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Value("${bank.service.url}")
    private String bankServiceUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("admin/accounts")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        return restTemplate.exchange(
                bankServiceUrl
                        + "/accounts"
                        + "/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
    }

    @GetMapping("admin/accounts/select-by-login/{login}")
    public ResponseEntity<List<AccountDTO>> getAllAccountsByUser(
            @PathVariable("login") String login
    ) {
        return restTemplate.exchange(
                bankServiceUrl
                        + "/accounts"
                        + "/" + login,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
    }

    @GetMapping("admin/accounts/select-by-id/{id}")
    public ResponseEntity<List<TransactionDTO>> getAccountHistoryById(
            @PathVariable("id") long id
    ) {
        return restTemplate.exchange(
                bankServiceUrl
                        + "/accounts"
                        + "/transactions"
                        + "/filter-by-accountId"
                        + "/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
    }

    @PostMapping("client/{account_id}/deposit/{amount}")
    public ResponseEntity<String> deposit(
            @PathVariable("account_id") long accountId,
            @PathVariable("amount") BigDecimal amount,
            Authentication authentication) {
        requireUserHasAccountId(authentication, accountId);
        return restTemplate.exchange(
                bankServiceUrl
                        + "/accounts"
                        + "/" + accountId
                        + "/deposit"
                        + "/" + amount,
                HttpMethod.POST,
                null,
                String.class
        );
    }

    @PostMapping("client/{account_id}/withdraw/{amount}")
    public ResponseEntity<String> withdraw(
            @PathVariable("account_id") long accountId,
            @PathVariable("amount") BigDecimal amount,
            Authentication authentication) {
        requireUserHasAccountId(authentication, accountId);
        return restTemplate.exchange(
                bankServiceUrl
                        + "/accounts"
                        + "/" + accountId
                        + "/withdraw"
                        + "/" + amount,
                HttpMethod.POST,
                null,
                String.class
        );
    }

    @PostMapping("client/{account_id}/transfer/{receiver_id}/{amount}")
    public ResponseEntity<String> transfer(
            @PathVariable("account_id") long accountId,
            @PathVariable("receiver_id") long receiverId,
            @PathVariable("amount") BigDecimal amount,
            Authentication authentication) {
        requireUserHasAccountId(authentication, accountId);
        return restTemplate.exchange(
                bankServiceUrl
                        + "/accounts"
                        + "/" + accountId
                        + "/transfer"
                        + "/" + receiverId
                        + "/" + amount,
                HttpMethod.POST,
                null,
                String.class
        );
    }

    @GetMapping("client/accounts")
    public ResponseEntity<List<AccountDTO>> getCurrentUserAccounts(Authentication authentication) {
        return restTemplate.exchange(
                bankServiceUrl
                        + "/accounts"
                        + "/" + authentication.getName(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
    }

    @GetMapping("client/{account_id}")
    public ResponseEntity<AccountDTO> getCurrentUserAccountById(
            @PathVariable("account_id") long accountId,
            Authentication authentication) {
        ResponseEntity<List<AccountDTO>> userAccounts = getCurrentUserAccounts(authentication);
        return new ResponseEntity<>(accountService.getAccount(
                userAccounts.getBody(),
                accountId
        ), HttpStatus.OK);
    }

    @PostMapping("client/create-account")
    public ResponseEntity<AccountDTO> createAccount(Authentication authentication) {
        return restTemplate.exchange(
                bankServiceUrl
                        + "/accounts"
                        + "/create-account"
                        + "/" + authentication.getName(),
                HttpMethod.POST,
                null,
                new ParameterizedTypeReference<>() {}
        );
    }

    private void requireUserHasAccountId(Authentication authentication, long accountId) {
        ResponseEntity<List<AccountDTO>> userAccounts = getCurrentUserAccounts(authentication);
        accountService.getAccount(userAccounts.getBody(), accountId);
    }
}
