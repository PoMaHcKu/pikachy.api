package by.itra.pikachy.api.mapper;

import by.itra.pikachy.api.dto.PostDto;
import by.itra.pikachy.api.entity.Genre;
import by.itra.pikachy.api.entity.Post;
import by.itra.pikachy.api.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {CommentaryMapper.class, SectionMapper.class})
public interface PostMapper {
    String DATE_FORMAT = "HH:mm dd.MM.yyyy";

    @Mappings({
            @Mapping(source = "created", target = "created", dateFormat = DATE_FORMAT),
            @Mapping(source = "updated", target = "updated", dateFormat = DATE_FORMAT)
    })
    PostDto toDto(Post post);

    Post toEntity(PostDto postDto);

    default User toEntity(int user) {
        User u = new User();
        u.setId(user);
        return u;
    }

    default int toDto(User entity) {
        return entity.getId();
    }

    default Genre toEntity(String genreName) {
        Genre genre = new Genre();
        genre.setGenreName(genreName);
        return genre;
    }

    default String toDto(Genre genre) {
        return genre.getGenreName();
    }
}