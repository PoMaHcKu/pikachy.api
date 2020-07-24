package by.itra.pikachy.api.mapper;

import by.itra.pikachy.api.dto.SectionDto;
import by.itra.pikachy.api.entity.Post;
import by.itra.pikachy.api.entity.Section;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SectionMapper {

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
}