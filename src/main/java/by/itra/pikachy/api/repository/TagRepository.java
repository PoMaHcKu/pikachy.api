package by.itra.pikachy.api.repository;

import by.itra.pikachy.api.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Tag findByTagName(String tagName);

    List<Tag> countBy(int count);
}
