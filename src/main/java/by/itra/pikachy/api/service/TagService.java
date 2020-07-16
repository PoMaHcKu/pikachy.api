package by.itra.pikachy.api.service;

import by.itra.pikachy.api.entity.Tag;
import by.itra.pikachy.api.mapper.TagMapper;
import by.itra.pikachy.api.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public List<Tag> saveAll(List<Tag> tags) {
        return tags.stream().map(tag -> {
            Tag fromDB = tagRepository.findByTagName(tag.getTagName());
            if (fromDB == null) {
                return tagRepository.save(tag);
            }
            return fromDB;
        }).collect(Collectors.toList());
    }

    public List<String> getAllTags() {
        return tagRepository.findAll().stream().map(tagMapper::toDto).collect(Collectors.toList());
    }

    public Tag getByTagName(String tagName) {
        return tagRepository.findByTagName(tagName);
    }
}