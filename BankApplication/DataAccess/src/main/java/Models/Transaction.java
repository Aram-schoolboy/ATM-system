package Models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Модель транзакции между счетами.
 */
@Entity
@Table(name = "transactions")
public class Transaction {

    /** Идентификатор транзакции */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Время совершения транзакции */
    @Column(nullable = false)
    private LocalDateTime timestamp;

    /** Счёта отправителя */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_account_id", referencedColumnName = "id", nullable = false)
    private Account senderAccount;

    /** Счёта получателя */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_account_id", referencedColumnName = "id", nullable = false)
    private Account receiverAccount;

    /** Сумма транзакции */
    @Column(nullable = false)
    private BigDecimal amount;

    /** Тип транзакции */
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    /**
     * Создает новую транзакцию.
     *
     * @param timestamp       время совершения транзакции
     * @param senderAccount идентификатор счёта отправителя
     * @param receiverAccount идентификатор счёта получателя
     * @param amount          сумма транзакции
     * @param transactionType тип транзакции
     */
    public Transaction(LocalDateTime timestamp, Account senderAccount, Account receiverAccount, BigDecimal amount, TransactionType transactionType) {
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.timestamp = timestamp;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public Transaction() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Account getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(Account senderAccount) {
        this.senderAccount = senderAccount;
    }

    public Account getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(Account receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
