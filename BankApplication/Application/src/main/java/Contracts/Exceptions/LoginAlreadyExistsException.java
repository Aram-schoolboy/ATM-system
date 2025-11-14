package Contracts.Exceptions;

/**
 * Исключение при попытке зарегистрировать уже существующий логин.
 */
public class LoginAlreadyExistsException extends RuntimeException {
    public LoginAlreadyExistsException(String login) {
        super("Логин " + login + " уже занят");
    }
}
