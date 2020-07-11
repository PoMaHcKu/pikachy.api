package by.itra.pikachy.api.controller;

import by.itra.pikachy.api.entity.Genre;
import by.itra.pikachy.api.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<Genre> getAll() {
        return genreService.getGenres();
    }
}