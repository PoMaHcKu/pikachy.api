package by.itra.pikachy.api.mapper;

import by.itra.pikachy.api.entity.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    default Genre toEntity(String genreName) {
        Genre genre = new Genre();
        genre.setGenreName(genreName);
        return genre;
    }

    default String toDto(Genre genre) {
        return genre.getGenreName();
    }

}