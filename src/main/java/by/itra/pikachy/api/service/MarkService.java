package by.itra.pikachy.api.service;

import by.itra.pikachy.api.entity.Mark;
import by.itra.pikachy.api.repository.MarkRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MarkService {
    private final MarkRepository markRepository;

    public Mark getMark(int mark) {
        return markRepository.findByMark(mark);
    }

    public Mark update(Mark mark) {
        return markRepository.save(mark);
    }


}