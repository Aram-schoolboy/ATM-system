package Contracts.Exceptions;

/**
 * Исключение при попытке добавить самого себя в список друзей.
 */
public class FriendshipWithSelfException extends RuntimeException {
    public FriendshipWithSelfException() {
        super("Нельзя добавить самого себя в список друзей!");
    }
}
