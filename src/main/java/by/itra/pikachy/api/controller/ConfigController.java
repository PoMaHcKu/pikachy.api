package by.itra.pikachy.api.controller;

import by.itra.pikachy.api.service.PostSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/config")
public class ConfigController {
    private final PostSearchService postSearchService;
    
    @GetMapping("indexes")
    public void createIndexes() {
        postSearchService.updateIndexes();
    }
}
