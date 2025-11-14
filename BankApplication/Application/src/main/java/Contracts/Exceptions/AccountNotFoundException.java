package Contracts.Exceptions;

/**
 * Исключение при попытке обратиться к несуществующему счёту.
 */
public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(long id) {
        super("Счет с номером " + id + " не существует");
    }
}
