package by.itra.pikachy.api.mapper;

import by.itra.pikachy.api.dto.CommentaryDto;
import by.itra.pikachy.api.entity.Commentary;
import by.itra.pikachy.api.entity.Post;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = {UserMapper.class}, componentModel = "spring")
public interface CommentaryMapper {
    @Mappings({
            @Mapping(target = "created", source = "created", dateFormat = "hh:mm dd.MM.yy"),
    })
    CommentaryDto fromCommentary(Commentary commentary);

    @IterableMapping(dateFormat = "hh:mm dd.MM.yyyy")
    Commentary toCommentary(CommentaryDto commentaryDto);

    default Post toEntity(int post) {
        Post p = new Post();
        p.setId(post);
        return p;
    }

    default int toDto(Post post) {
        return post.getId();
    }
}