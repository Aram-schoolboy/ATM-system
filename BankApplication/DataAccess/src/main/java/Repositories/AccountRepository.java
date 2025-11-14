package Repositories;

import Models.Account;
import Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Интерфейс репозитория для работы с банковскими счетами.
 * Определяет операции проверки существования счета, получения баланса,
 * управления балансом и получения списка счетов пользователя.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    /**
     * Проверяет, существует ли счет с указанным идентификатором.
     *
     * @param id идентификатор счета
     * @return true, если счет существует, иначе false
     */
    boolean existsById(long id);

    List<Account> findByOwner(User owner);

    /**
     * Возвращает список всех счетов пользователя по его логину.
     *
     * @param login логин пользователя
     * @return список счетов пользователя
     */
    List<Account> findByOwnerLogin(String login);

}