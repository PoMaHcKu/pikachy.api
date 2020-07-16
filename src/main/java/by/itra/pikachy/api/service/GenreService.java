package by.itra.pikachy.api.service;

import by.itra.pikachy.api.entity.Genre;
import by.itra.pikachy.api.mapper.GenreMapper;
import by.itra.pikachy.api.repository.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

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