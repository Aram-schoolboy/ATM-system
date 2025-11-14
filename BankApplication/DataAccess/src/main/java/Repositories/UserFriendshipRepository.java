package Repositories;

import Models.Friendship;
import Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для управления дружескими связями между пользователями.
 */
@Repository
public interface UserFriendshipRepository extends JpaRepository<Friendship, Long> {
    boolean existsByUser1AndUser2(User user1, User user2);
    Friendship findByUser1AndUser2(User user1, User user2);
    List<Friendship> findAllByUser1(User user);
}
