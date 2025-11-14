package Contracts.Exceptions;

/**
 * Исключение при попытке провести операцию, когда на счёте недостаточно средств.
 */
public class NotEnoughBalanceException extends RuntimeException {
    public NotEnoughBalanceException(String id) {
        super("На счету " + id + " недостаточно средств");
    }
}
