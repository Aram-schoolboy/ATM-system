package Contracts;

import Models.Account;
import Models.Transaction;
import Models.TransactionType;
import Models.User;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.math.BigDecimal;
import java.util.List;

/**
 * Сервис для работы со счетами пользователей.
 */
public interface AccountService {

    /**
     * Создает новый счёт для указанного пользователя.
     *
     * @param ownerLogin пользователь-владелец нового счёта
     * @return идентификатор созданного счёта
     */
    Account addAccount(String ownerLogin);

    /**
     * Выбирает счёт по идентификатору для указанного логина.
     *
     * @param id    идентификатор счёта
     * @param user  пользователь
     */
    void selectAccount(long id, User user);

    void selectAccount(long id);

    /**
     * Возвращает баланс текущего выбранного счёта.
     *
     * @return баланс счёта
     */
    BigDecimal getBalance();

    /**
     * Возвращает пользователя текущего счёта.
     *
     * @return логин пользователя
     */
    User getOwner();

    /**
     * Снимает указанную сумму с текущего счёта.
     *
     * @param amount сумма для снятия
     */
    void withdraw(BigDecimal amount);

    /**
     * Вносит указанную сумму на текущий счёт.
     *
     * @param amount сумма для внесения
     */
    void deposit(BigDecimal amount);

    /**
     * Переводит указанную сумму с текущего счёта на другой счёт по идентификатору.
     *
     * @param id     идентификатор счёта-получателя
     * @param amount сумма перевода
     */
    void transfer(long id, BigDecimal amount);

    /**
     * Возвращает список всех счётов пользователя по логину.
     *
     * @param login логин пользователя
     * @return список счётов пользователя
     */
    List<Account> getUserAccountsByLogin(String login);

    List<Account> getAllAccounts();

    List<Transaction> getTransactionsByType(TransactionType type);

    List<Transaction> getTransactionsByAccountId(long accountId);
}
