package Contracts.Exceptions;

/**
 * Исключение при попытке добавить в друзья пользователей, которые уже являются друзьями.
 */
public class UsersAlreadyFriendsException extends RuntimeException {
    public UsersAlreadyFriendsException(String login1, String login2) {
        super("Пользователи " + login1 + " и " + login2 + " уже являются друзьями");
    }
}
