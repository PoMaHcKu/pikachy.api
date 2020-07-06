package by.itra.pikachy.api.repository;

import by.itra.pikachy.api.entity.Commentary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentaryRepository extends JpaRepository<Commentary, Integer> {
    Page<Commentary> findByPostId(int postId, Pageable pageable);
}