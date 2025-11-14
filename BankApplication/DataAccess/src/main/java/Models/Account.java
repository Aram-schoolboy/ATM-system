package Models;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * Модель счёта пользователя.
 */
@Entity
@Table(name = "accounts")
public class Account {

    /** Идентификатор счёта */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Баланс счёта */
    @Column(nullable = false)
    private BigDecimal balance;

    /** Владелец счёта */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id", nullable = false)
    private User owner;

    /**
     * Создает новый счёт.
     *
     * @param owner логин владельца счёта
     */
    public Account(User owner) {
        this.owner = owner;
        this.balance = BigDecimal.ZERO;
    }

    public Account() {}

    /**
     * Возвращает идентификатор счёта.
     *
     * @return идентификатор счёта
     */
    public long getId() {
        return id;
    }

    /**
     * Возвращает логин владельца счёта.
     *
     * @return логин владельца
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Возвращает текущий баланс счёта.
     *
     * @return баланс счёта
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Устанавливает новый баланс счёта.
     *
     * @param balance новый баланс
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setId(long id) {
        this.id = id;
    }
}
