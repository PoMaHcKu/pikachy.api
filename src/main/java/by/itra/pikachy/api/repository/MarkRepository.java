package by.itra.pikachy.api.repository;

import by.itra.pikachy.api.entity.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkRepository extends JpaRepository<Mark, Integer> {
    Mark findByMark(int mark);
}