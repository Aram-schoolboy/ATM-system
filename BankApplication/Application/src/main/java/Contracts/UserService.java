package Contracts;

import Models.Gender;
import Models.HairColor;
import Models.User;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

/**
 * Сервис для работы с пользователями и их друзьями.
 */
public interface UserService {

    /**
     * Создает нового пользователя.
     *
     * @param login логин пользователя
     * @param name имя пользователя
     * @param age возраст пользователя
     * @param gender гендер пользователя
     * @param hairColor цвет волос пользователя
     */
    User createUser(String login, String name, int age, Gender gender, HairColor hairColor);

    /**
     * Возвращает текущего пользователя.
     *
     * @return объект пользователя
     */
    User getUser();

    /**
     * Добавляет пользователя в список друзей по логину.
     *
     * @param newFriendLogin логин пользователя, которого нужно добавить в друзья
     */
    void addFriend(String newFriendLogin);

    /**
     * Удаляет пользователя из списка друзей по логину.
     *
     * @param friendLogin логин пользователя-друга, которого нужно удалить из друзей
     */
    void removeFriend(String friendLogin);

    List<User> getFriends();

    /**
     * Авторизует пользователя по логину.
     *
     * @param login логин пользователя
     */
    void userLogin(String login);

    /**
     * Проверяет, существует ли логин.
     *
     * @param login проверяемый логин
     * @return true, если логин существует, иначе false
     */
    boolean loginExists(String login);

    List<User> getAllUsersByHairColor(HairColor hairColor);
    List<User> getAllUsersByGender(Gender gender);
}
