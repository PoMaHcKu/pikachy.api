package by.itra.pikachy.api.repository;

import by.itra.pikachy.api.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Tag findByTagName(String tagName);

    @Query(value = "select * from tag t limit ?", nativeQuery = true)
    List<Tag> getCount(int limit);
}
