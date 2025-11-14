package com.mybank.storage.Messages;

import com.mybank.storage.Models.EventTypes.AccountEvent;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts_balance_changed_events")
public class AccountBalanceChangedMessage implements AccountEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "new_balance", nullable = false)
    private BigDecimal newBalance;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    public AccountBalanceChangedMessage(Long accountId, BigDecimal newBalance, LocalDateTime date) {
        this.accountId = accountId;
        this.newBalance = newBalance;
        this.date = date;
    }

    public AccountBalanceChangedMessage() {

    }

    public AccountBalanceChangedMessage(Messages.AccountBalanceChangedMessage accountBalanceChangedMessage) {
        this.accountId = accountBalanceChangedMessage.accountId();
        this.newBalance = accountBalanceChangedMessage.newBalance();
        this.date = accountBalanceChangedMessage.date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(BigDecimal newBalance) {
        this.newBalance = newBalance;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }
}

