package by.itra.pikachy.api.service;

import by.itra.pikachy.api.entity.Tag;
import by.itra.pikachy.api.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public Tag getByTagName(String tagName) {
        return tagRepository.findByTagName(tagName);
    }
}