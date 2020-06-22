package by.itra.pikachy.api.repository;

import by.itra.pikachy.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByVerificationToken(String token);
}
