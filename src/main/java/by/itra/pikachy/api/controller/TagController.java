package by.itra.pikachy.api.controller;

import by.itra.pikachy.api.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
@AllArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping
    public List<String> getCountTags(@RequestParam int count) {
        return tagService.getCountTags(count);
    }

    @PatchMapping
    public List<String> getAll(@RequestParam int page) {
        return tagService.getAllTags(page);
    }
}