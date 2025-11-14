package Repositories;

import Models.Gender;
import Models.HairColor;
import Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для управления пользователями.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByLogin(String login);
    User findByLogin(String login);
    List<User> findAllByHairColor(HairColor hairColor);
    List<User> findAllByGender(Gender gender);
}
