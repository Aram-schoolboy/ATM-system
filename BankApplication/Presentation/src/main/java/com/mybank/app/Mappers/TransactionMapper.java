package com.mybank.app.Mappers;

import Models.Transaction;
import com.mybank.app.DTO.TransactionDTO;

public class TransactionMapper {
    public static TransactionDTO toDTO(Transaction transaction) {
        return new  TransactionDTO(
                transaction.getId(),
                transaction.getTimestamp(),
                AccountMapper.toDto(transaction.getSenderAccount()),
                AccountMapper.toDto(transaction.getReceiverAccount()),
                transaction.getAmount(),
                transaction.getTransactionType()
        );
    }
}
