package by.itra.pikachy.api.repository;

import by.itra.pikachy.api.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Tag findByTagName(String tagName);
}
