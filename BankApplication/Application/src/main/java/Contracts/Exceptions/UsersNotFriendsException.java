package Contracts.Exceptions;

/**
 * Исключение при попытке удалить из списка друзей пользователей, которые не являются друзьями.
 */
public class UsersNotFriendsException extends RuntimeException {

    public UsersNotFriendsException(String login1, String login2) {
        super("Пользователи " + login1 + " и " + login2 + " не являются друзьями");
    }
}
