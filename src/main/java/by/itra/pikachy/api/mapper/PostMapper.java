package by.itra.pikachy.api.mapper;

import by.itra.pikachy.api.dto.PostDto;
import by.itra.pikachy.api.entity.Genre;
import by.itra.pikachy.api.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CommentaryMapper.class, UserMapper.class})
public interface PostMapper {

    PostDto toDto(Post post);

    Post toEntity(PostDto postDto);

    default Genre toEntity(String genreName) {
        Genre genre = new Genre();
        genre.setGenreName(genreName);
        return genre;
    }

    default String toDto(Genre genre) {
        return genre.getGenreName();
    }
}