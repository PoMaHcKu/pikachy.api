package by.itra.pikachy.api.service;

import by.itra.pikachy.api.entity.Section;
import by.itra.pikachy.api.repository.SectionRepository;
import org.springframework.stereotype.Service;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public Section create(Section section) {
        return sectionRepository.save(section);
    }
}