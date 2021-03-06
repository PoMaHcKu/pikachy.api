package by.itra.pikachy.api.service;

import by.itra.pikachy.api.dto.SectionDto;
import by.itra.pikachy.api.entity.Section;
import by.itra.pikachy.api.mapper.SectionMapper;
import by.itra.pikachy.api.repository.SectionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@AllArgsConstructor
@Transactional
public class SectionService {
    private final SectionRepository sectionRepository;
    private final SectionMapper sectionMapper;
    private final AuthenticationService authenticationService;

    public SectionDto getSection(int id) {
        return sectionMapper.fromSection(sectionRepository.getOne(id));
    }

    public SectionDto like(int sectionId, Principal principal) {
        Section section = sectionRepository.getOne(sectionId);
        section.getLikes().add(authenticationService.getAuthenticatedUser(principal));
        return sectionMapper.fromSection(sectionRepository.save(section));
    }

    public SectionDto dislike(int sectionId, Principal principal) {
        Section section = sectionRepository.getOne(sectionId);
        section.getLikes().remove(authenticationService.getAuthenticatedUser(principal));
        return sectionMapper.fromSection(sectionRepository.save(section));
    }
}