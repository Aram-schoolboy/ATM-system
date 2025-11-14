package Contracts.Exceptions;

/**
 * Исключение, выбрасываемое при неудачной попытке выбора счёта по его идентификатору.
 */
public class AccountLoginFailureException extends RuntimeException {
    public AccountLoginFailureException(long id) {
        super("Ошибка выбора счета с id: " + id);
    }
}
