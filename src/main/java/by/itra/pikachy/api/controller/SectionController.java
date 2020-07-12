package by.itra.pikachy.api.controller;

import by.itra.pikachy.api.dto.SectionDto;
import by.itra.pikachy.api.service.SectionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/section")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping("{id}")
    public SectionDto like(@PathVariable int id, @AuthenticationPrincipal Principal principal) {
        return sectionService.like(id, principal);
    }

    @DeleteMapping("{id}")
    public SectionDto dislike(@PathVariable int id, @AuthenticationPrincipal Principal principal) {
        return sectionService.dislike(id, principal);
    }
}