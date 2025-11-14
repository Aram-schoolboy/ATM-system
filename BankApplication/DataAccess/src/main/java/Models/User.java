package Models;

import jakarta.persistence.*;

/**
 * Модель пользователя.
 */
@Entity
@Table(name = "users")
public class User {

    /** Идентификатор пользователя */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    /** Логин пользователя */
    @Column(nullable = false, unique = true, length = Integer.MAX_VALUE)
    private String login;

    /** Имя пользователя */
    @Column(nullable = false, length = Integer.MAX_VALUE)
    private String name;

    /** Возраст пользователя */
    @Column(nullable = false)
    private int age;

    /** Пол пользователя */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    /** Цвет волос пользователя */
    @Enumerated(EnumType.STRING)
    @Column(name = "haircolor", nullable = false)
    private HairColor hairColor;

    /**
     * Создает нового пользователя.
     *
     * @param login     логин пользователя
     * @param name      имя пользователя
     * @param age       возраст пользователя
     * @param gender    пол пользователя
     * @param hairColor цвет волос пользователя
     */
    public User(String login, String name, int age, Gender gender, HairColor hairColor) {
        this.login = login;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.hairColor = hairColor;
    }

    public User() {}

    /** @return возраст пользователя */
    public long getUserId() {
        return userId;
    }

    /** @return логин пользователя */
    public String getLogin() {
        return login;
    }

    /** @param login новый логин пользователя */
    public void setLogin(String login) {
        this.login = login;
    }

    /** @return имя пользователя */
    public String getName() {
        return name;
    }

    /** @param name новое имя пользователя */
    public void setName(String name) {
        this.name = name;
    }

    /** @return возраст пользователя */
    public int getAge() {
        return age;
    }

    /** @param age новый возраст пользователя */
    public void setAge(int age) {
        this.age = age;
    }

    /** @return пол пользователя */
    public Gender getGender() {
        return gender;
    }

    /** @param gender новый пол пользователя */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /** @return цвет волос пользователя */
    public HairColor getHairColor() {
        return hairColor;
    }

    /** @param hairColor новый цвет волос пользователя */
    public void setHairColor(HairColor hairColor) {
        this.hairColor = hairColor;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Возвращает строковое представление пользователя.
     *
     * @return информация о пользователе: логин, имя, возраст, пол и цвет волос
     */
    @Override
    public String toString() {
        return "Info about user with login <" + login + ">\n" +
                "name: " + name + "\n" +
                "age: " + age + "\n" +
                "gender: " + gender + "\n" +
                "hair colour: " + hairColor + "\n";
    }
}
