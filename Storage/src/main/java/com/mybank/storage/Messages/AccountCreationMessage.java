package com.mybank.storage.Messages;

import com.mybank.storage.Models.EventTypes.AccountEvent;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "accounts_creation_events")
public class AccountCreationMessage implements AccountEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "owner_login", nullable = false)
    private String ownerLogin;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    public AccountCreationMessage(String ownerLogin, Long accountId, LocalDateTime date) {
        this.ownerLogin = ownerLogin;
        this.accountId = accountId;
        this.date = date;
    }

    public AccountCreationMessage() {

    }

    public AccountCreationMessage(Messages.AccountCreationMessage accountCreationMessage) {
        this.ownerLogin = accountCreationMessage.ownerLogin();
        this.accountId = accountCreationMessage.accountId();
        this.date = accountCreationMessage.date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }
}

