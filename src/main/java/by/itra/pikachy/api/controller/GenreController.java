package by.itra.pikachy.api.controller;

import by.itra.pikachy.api.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genre")
@AllArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public List<String> getAll() {
        return genreService.getGenres();
    }
}