package by.itra.pikachy.api.mapper;

import by.itra.pikachy.api.entity.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {

    default Tag toEntity(String tagName) {
        Tag tag = new Tag();
        tag.setTagName(tagName);
        return tag;
    }

    default String toDto(Tag tag) {
        return tag.getTagName();
    }
}