package by.itra.pikachy.api.repository;

import by.itra.pikachy.api.entity.Genre;
import by.itra.pikachy.api.entity.Post;
import by.itra.pikachy.api.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    Page<Post> findByTagsIn(Pageable pageable, List<Tag> tags);

    Page<Post> findByGenre(Pageable pageable, Genre genre);
}