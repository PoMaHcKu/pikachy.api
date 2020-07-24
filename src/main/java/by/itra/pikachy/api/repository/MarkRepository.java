package by.itra.pikachy.api.repository;

import by.itra.pikachy.api.entity.Mark;
import by.itra.pikachy.api.entity.Post;
import by.itra.pikachy.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkRepository extends JpaRepository<Mark, Integer> {
    Mark findByPostAndUser(Post post, User user);
}