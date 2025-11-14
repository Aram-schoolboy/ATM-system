package Services;

import Contracts.Exceptions.FriendshipWithSelfException;
import Contracts.Exceptions.UsersAlreadyFriendsException;
import Contracts.Exceptions.UsersNotFriendsException;
import Contracts.UserFriendshipService;
import Models.Friendship;
import Models.User;
import Repositories.UserFriendshipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFriendshipServiceImpl implements UserFriendshipService {
    private final UserFriendshipRepository userFriendshipRepository;

    public UserFriendshipServiceImpl(UserFriendshipRepository userFriendshipRepository) {
        this.userFriendshipRepository = userFriendshipRepository;
    }

    @Override
    public boolean friendshipExists(User user1, User user2) {
        return userFriendshipRepository.existsByUser1AndUser2(user1, user2);
    }

    @Override
    public void addFriendship(User user1, User user2) {
        if (user1.getLogin().equals(user2.getLogin())) {
            throw new FriendshipWithSelfException();
        }
        if (friendshipExists(user1, user2)) {
            throw new UsersAlreadyFriendsException(user1.getLogin(), user2.getLogin());
        }
        userFriendshipRepository.save(new Friendship(user1, user2));
        userFriendshipRepository.save(new Friendship(user2, user1));
    }

    @Override
    public void removeFriendship(User user1, User user2) {
        if (!friendshipExists(user1, user2)) {
            throw new UsersNotFriendsException(user1.getLogin(), user2.getLogin());
        }
        Friendship friendship1 = userFriendshipRepository.findByUser1AndUser2(user1, user2);
        userFriendshipRepository.delete(friendship1);
        Friendship friendship2 = userFriendshipRepository.findByUser1AndUser2(user2, user1);
        userFriendshipRepository.delete(friendship2);
    }

    @Override
    public List<Friendship> getFriendships(User user) {
        return userFriendshipRepository.findAllByUser1(user);
    }
}
