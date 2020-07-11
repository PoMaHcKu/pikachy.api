package by.itra.pikachy.api.service;

import by.itra.pikachy.api.entity.Genre;
import by.itra.pikachy.api.mapper.GenreMapper;
import by.itra.pikachy.api.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Autowired
    public GenreService(GenreRepository genreRepository, GenreMapper genreMapper) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
    }

    public Genre findByGenreName(String genreName) {
        return genreRepository.findByGenreName(genreName);
    }

    public List<String> getGenres() {
        return genreRepository.findAll()
                .stream()
                .map(genreMapper::toDto)
                .collect(Collectors.toList());
    }
}