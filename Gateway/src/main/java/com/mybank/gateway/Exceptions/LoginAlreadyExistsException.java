package com.mybank.gateway.Exceptions;

public class LoginAlreadyExistsException extends RuntimeException {
    public LoginAlreadyExistsException(String login) {
        super("login is already exists: " + login);
    }
}
