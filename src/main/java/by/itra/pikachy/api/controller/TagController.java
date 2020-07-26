package by.itra.pikachy.api.controller;

import by.itra.pikachy.api.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tag")
@AllArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping
    public List<String> getAll() {
        return tagService.getAllTags();
    }
}