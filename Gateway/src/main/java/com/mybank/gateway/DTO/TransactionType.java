package com.mybank.gateway.DTO;

/**
 * Тип транзакции между счетами.
 */
public enum TransactionType {
    /** Внесение средств на счёт */
    DEPOSIT,
    /** Снятие средств со счёта */
    WITHDRAW,
    /** Перевод средств между счетами */
    TRANSFER
}
