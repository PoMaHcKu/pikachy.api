package by.itra.pikachy.api.mapper;

import by.itra.pikachy.api.dto.CommentaryDto;
import by.itra.pikachy.api.entity.Commentary;
import by.itra.pikachy.api.entity.Post;
import by.itra.pikachy.api.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentaryMapper {

    @Mapping(target = "created", source = "created", dateFormat = "hh:mm dd.MM.yy")
    CommentaryDto toDto(Commentary commentary);

    Commentary toEntity(CommentaryDto commentaryDto);

    default Post toEntityPost(int post) {
        Post p = new Post();
        p.setId(post);
        return p;
    }

    default int toDtoPost(Post post) {
        return post.getId();
    }

    default User toEntityUser(int userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }

    default int toDtoUser(User user) {
        return user.getId();
    }
}