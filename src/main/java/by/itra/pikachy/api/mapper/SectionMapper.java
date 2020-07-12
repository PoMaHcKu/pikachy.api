package by.itra.pikachy.api.mapper;

import by.itra.pikachy.api.dto.SectionDto;
import by.itra.pikachy.api.entity.Post;
import by.itra.pikachy.api.entity.Section;
import by.itra.pikachy.api.entity.User;
import by.itra.pikachy.api.security.UserDetailsImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.Authentication;
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

    default boolean isLiked(Set<User> users) {
        String username = getAuthenticatedUserName();
        for (User usr : users) {
            if (usr.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    default String getAuthenticatedUserName() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String anonymous = authentication.getPrincipal().toString();
        if (anonymous.contains("anonymousUser")) {
            return null;
        }
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        return user.getUsername();
    }
}