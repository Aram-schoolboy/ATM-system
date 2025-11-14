package com.mybank.gateway.Exceptions;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(long id) {
        super("account not found: " + id);
    }
}