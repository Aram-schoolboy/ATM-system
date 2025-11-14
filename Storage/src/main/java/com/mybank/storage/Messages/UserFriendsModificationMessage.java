package com.mybank.storage.Messages;

import com.mybank.storage.Models.EventTypes.ClientEvent;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users_friends_modification_events")
public class UserFriendsModificationMessage implements ClientEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "authorLogin", nullable = false)
    private String authorLogin;

    @Column(name = "targetLogin", nullable = false)
    private String targetLogin;

    @Column(name = "operationType", nullable = false)
    private String operationType;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    public UserFriendsModificationMessage(String authorLogin, String targetLogin, String operationType, LocalDateTime date) {
        this.authorLogin = authorLogin;
        this.targetLogin = targetLogin;
        this.operationType = operationType;
        this.date = date;
    }

    public UserFriendsModificationMessage() {

    }

    public UserFriendsModificationMessage(Messages.UserFriendsModificationMessage userFriendsModificationMessage) {
        this.authorLogin = userFriendsModificationMessage.authorLogin();
        this.targetLogin = userFriendsModificationMessage.targetLogin();
        this.operationType = userFriendsModificationMessage.operationType();
        this.date = userFriendsModificationMessage.date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorLoginLogin() {
        return authorLogin;
    }

    public void setAuthorLogin(String authorLogin) {
        this.authorLogin = authorLogin;
    }

    public String getTargetLoginLogin() {
        return targetLogin;
    }

    public void setTargetLogin(String targetLogin) {
        this.targetLogin = targetLogin;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }
}

