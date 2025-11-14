package Contracts.Exceptions;

/**
 * Исключение при попытке найти пользователя по логину, которого не существует.
 */
public class UserNotFoundByLoginException extends RuntimeException {
    public UserNotFoundByLoginException(String login) {
        super("Пользователь с логином " + login + " не найден");
    }
}
