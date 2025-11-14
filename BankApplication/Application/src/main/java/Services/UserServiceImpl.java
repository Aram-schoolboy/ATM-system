package Services;

import Contracts.UserFriendshipService;
import Models.Friendship;
import Models.Gender;
import Models.HairColor;
import Producers.UserEventProducer;
import Repositories.UserRepository;
import Contracts.Exceptions.*;
import Contracts.UserService;
import Models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserEventProducer userEventProducer;
    private final UserRepository userRepository;
    private final UserFriendshipService userFriendshipService;
    private User currentUser;

    public UserServiceImpl(UserRepository userRepository, UserFriendshipService userFriendshipService, UserEventProducer userEventProducer) {
        this.userRepository = userRepository;
        this.userFriendshipService = userFriendshipService;
        this.userEventProducer = userEventProducer;
    }

    @Override
    public User createUser(String login, String name, int age, Gender gender, HairColor hairColor) {
        if (loginExists(login)) {
            throw new LoginAlreadyExistsException(login);
        }
        User user = new User(login, name, age, gender, hairColor);
        userRepository.save(user);
        userEventProducer.ProduceUserCreationMessage(user);
        return user;
    }

    @Override
    public User getUser() {
        return currentUser;
    }

    @Override
    public void addFriend(String newFriendLogin) {
        requireUserExistsByLogin(newFriendLogin);
        User newFriendUser = userRepository.findByLogin(newFriendLogin);
        userFriendshipService.addFriendship(currentUser, newFriendUser);
        userEventProducer.ProduceUserFriendsModificationMessage(currentUser.getLogin(), newFriendLogin, FriendOperation.ADD.toString());
    }

    @Override
    public void removeFriend(String friendLogin) {
        requireUserExistsByLogin(friendLogin);
        User friendUser = userRepository.findByLogin(friendLogin);
        if (!userFriendshipService.friendshipExists(currentUser, friendUser)) {
            throw new UsersNotFriendsException(currentUser.getLogin(), friendLogin);
        }
        userFriendshipService.removeFriendship(currentUser, friendUser);
        userEventProducer.ProduceUserFriendsModificationMessage(currentUser.getLogin(), friendLogin, FriendOperation.REMOVE.toString());

    }

    @Override
    public List<User> getFriends() {
        return userFriendshipService.getFriendships(currentUser).stream()
                .map(Friendship::getUser2)
                .toList();
    }

    @Override
    public void userLogin(String login) {
        requireUserExistsByLogin(login);
        currentUser = userRepository.findByLogin(login);
    }

    @Override
    public boolean loginExists(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public List<User> getAllUsersByHairColor(HairColor hairColor) {
        return userRepository.findAllByHairColor(hairColor);
    }

    @Override
    public List<User> getAllUsersByGender(Gender gender) {
        return userRepository.findAllByGender(gender);
    }

    private void requireUserExistsByLogin(String login) {
        if (!loginExists(login)) {
            throw new UserNotFoundByLoginException(login);
        }
    }


}