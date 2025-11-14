package com.mybank.storage.Messages;

import com.mybank.storage.Models.EventTypes.ClientEvent;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users_creation_events")
public class UserCreationMessage implements ClientEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "hair_color", nullable = false)
    private String hairColor;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    public UserCreationMessage(Long userId, String login, String name, int age, String gender, String hairColor, LocalDateTime date) {
        this.userId = userId;
        this.login = login;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.hairColor = hairColor;
        this.date = date;
    }

    public UserCreationMessage() {

    }

    public UserCreationMessage(Messages.UserCreationMessage userCreationMessage) {
        this.userId = userCreationMessage.userId();
        this.login = userCreationMessage.login();
        this.name = userCreationMessage.name();
        this.age = userCreationMessage.age();
        this.gender = userCreationMessage.gender();
        this.hairColor = userCreationMessage.hairColor();
        this.date = userCreationMessage.date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHairColour() {
        return hairColor;
    }

    public void setHairColour(String hairColour) {
        this.hairColor = hairColour;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

