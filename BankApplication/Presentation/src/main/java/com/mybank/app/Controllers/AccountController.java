package com.mybank.app.Controllers;

import Contracts.AccountService;
import Models.TransactionType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mybank.app.DTO.AccountDTO;
import com.mybank.app.DTO.TransactionDTO;
import com.mybank.app.Mappers.AccountMapper;
import com.mybank.app.Mappers.TransactionMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "deposit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "deposit made successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @PostMapping("/{accountId}/deposit/{amount}")
    public ResponseEntity<String> deposit(
            @Parameter(description = "Id of the account to deposit")
            @PathVariable("accountId") long accountId,
            @Parameter(description = "Amount of money to deposit")
            @PathVariable("amount") BigDecimal amount) {
        accountService.selectAccount(accountId);
        accountService.deposit(amount);
        return ResponseEntity.ok("Deposit made successfully");
    }

    @Operation(summary = "withdraw")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "withdraw made successfully"),
            @ApiResponse(responseCode = "400", description = "Not enough money"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @PostMapping("/{accountId}/withdraw/{amount}")
    public ResponseEntity<String> withdraw(
            @Parameter(description = "Id of the account to withdraw")
            @PathVariable("accountId") long accountId,
            @Parameter(description = "Amount of money to withdraw")
            @PathVariable("amount") BigDecimal amount) {
        accountService.selectAccount(accountId);
        accountService.withdraw(amount);
        return ResponseEntity.ok("Withdraw made successfully");
    }

    @Operation(summary = "transfer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "transfer made successfully"),
            @ApiResponse(responseCode = "400", description = "Not enough money"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @PostMapping("/{accountId}/transfer/{receiverId}/{amount}")
    public ResponseEntity<String> transfer(
            @Parameter(description = "Id of the account to transfer")
            @PathVariable("accountId") long accountId,
            @Parameter(description = "Id of the receiverAccount to transfer")
            @PathVariable("receiverId") long receiverId,
            @Parameter(description = "Amount of money to transfer")
            @PathVariable("amount") BigDecimal amount) {
        accountService.selectAccount(accountId);
        accountService.transfer(receiverId, amount);
        return ResponseEntity.ok("Transfer made successfully");
    }

    @Operation(summary = "show balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Balance info provided"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping("/{accountId}/balance")
    public ResponseEntity<BigDecimal> getBalance(
            @Parameter(description = "Id of the account to show balance")
            @PathVariable("accountId") long accountId) {
        accountService.selectAccount(accountId);
        return ResponseEntity.ok(accountService.getBalance());
    }

    @Operation(summary = "make account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account created successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/create-account/{login}")
    public ResponseEntity<AccountDTO> createAccount(
            @Parameter(description = "Login of the user to create account")
            @PathVariable("login") String login) {
        return ResponseEntity.ok(AccountMapper.toDto(accountService.addAccount(login)));
    }

    @Operation(summary = "get all user accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accounts provided successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{login}")
    public ResponseEntity<List<AccountDTO>> getAllUserAccounts(
            @Parameter(description = "Login of the user to get accounts")
            @PathVariable("login") String login) {
        return ResponseEntity.ok(accountService.getUserAccountsByLogin(login).stream()
                .map(AccountMapper::toDto)
                .toList());
    }

    @Operation(summary = "get all user accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accounts provided successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/all")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts().stream()
                .map(AccountMapper::toDto)
                .toList());
    }

    @Operation(summary = "get transactions filtered by type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions filtered by type provided successfully")
    })
    @GetMapping("/transactions/filter-by-type/{type}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByType(
            @Parameter(description = "Selected transaction type")
            @PathVariable("type") TransactionType type
    ) {
        return ResponseEntity.ok(accountService.getTransactionsByType(type).stream()
                .map(TransactionMapper::toDTO)
                .toList());
    }

    @Operation(summary = "get transactions filtered by accountId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions filtered by accountId provided successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping("/transactions/filter-by-accountId/{accountId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAccountId(
            @Parameter(description = "Selected accountId")
            @PathVariable("accountId") long accountId
    ) {
        return ResponseEntity.ok(accountService.getTransactionsByAccountId(accountId).stream()
                .map(TransactionMapper::toDTO)
                .toList());
    }
}
