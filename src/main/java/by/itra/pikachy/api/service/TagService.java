package by.itra.pikachy.api.service;

import by.itra.pikachy.api.entity.Tag;
import by.itra.pikachy.api.repository.TagRepository;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    private final TagRepository tagRepository;


    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag getByTagName(String tagName) {
        return tagRepository.findByTagName(tagName);
    }
}