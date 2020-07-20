package by.itra.pikachy.api.controller;

import by.itra.pikachy.api.service.PostSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PostSearchService postSearchService;

    @GetMapping("/indexes")
    public void createIndexes() {
        postSearchService.updateIndexed();
    }
}
