package Repositories;

import Models.Account;
import Models.Transaction;
import Models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с транзакциями пользователей.
 * Предоставляет метод для добавления новых транзакций в систему.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllBySenderAccountOrReceiverAccount(Account senderAccount, Account receiverAccount);
    List<Transaction> findAllByTransactionType(TransactionType transactionType);
}