package by.itra.pikachy.api.service;

import by.itra.pikachy.api.entity.Genre;
import by.itra.pikachy.api.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre findByGenreName(String genreName) {
        return genreRepository.findByGenreName(genreName);
    }

    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }
}