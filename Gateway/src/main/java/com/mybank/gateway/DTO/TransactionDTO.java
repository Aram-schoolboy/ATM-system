package com.mybank.gateway.DTO;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDTO(Long id, LocalDateTime timestamp, AccountDTO senderAccount, AccountDTO receiverAccount, BigDecimal amount, TransactionType transactionType) {
}
