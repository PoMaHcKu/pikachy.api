package by.itra.pikachy.api.mapper;

import by.itra.pikachy.api.dto.SectionDto;
import by.itra.pikachy.api.entity.Post;
import by.itra.pikachy.api.entity.Section;
import by.itra.pikachy.api.entity.User;
import by.itra.pikachy.api.security.UserDetailsImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface SectionMapper {

    @Mapping(source = "likes", target = "countLike")
    @Mapping(source = "likes", target = "liked")
    SectionDto fromSection(Section section);

    Section toSection(SectionDto sectionDto);

    default Post toEntity(int post) {
        Post p = new Post();
        p.setId(post);
        return p;
    }

    default int toDto(Post entity) {
        return entity.getId();
    }

    default int getCountLike(Set<User> users) {
        return users.size();
    }

    default boolean is(Set<User> users) {
        for (User usr : users) {
            UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            if (usr.getUsername().equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }
}