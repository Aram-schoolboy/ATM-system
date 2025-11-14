package Contracts;

import Models.Friendship;
import Models.User;

import java.util.List;

public interface UserFriendshipService {
    boolean friendshipExists(User user1, User user2);
    void addFriendship(User user1, User user2);
    void removeFriendship(User user1, User user2);
    List<Friendship> getFriendships(User user);
}
