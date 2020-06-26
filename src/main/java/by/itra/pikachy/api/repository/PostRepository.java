package by.itra.pikachy.api.repository;

import by.itra.pikachy.api.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}