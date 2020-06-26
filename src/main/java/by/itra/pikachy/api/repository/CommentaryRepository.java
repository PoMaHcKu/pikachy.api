package by.itra.pikachy.api.repository;

import by.itra.pikachy.api.entity.Commentary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentaryRepository extends JpaRepository<Commentary, Integer> {
    List<Commentary> findByPostId(int postId);
}