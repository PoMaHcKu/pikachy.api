package by.itra.pikachy.api.mapper;

import by.itra.pikachy.api.dto.PostDto;
import by.itra.pikachy.api.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {CommentaryMapper.class, SectionMapper.class,
        GenreMapper.class, TagMapper.class})
public interface PostMapper {
    String DATE_FORMAT = "HH:mm dd.MM.yyyy";

    @Mappings({
            @Mapping(source = "created", target = "created", dateFormat = DATE_FORMAT),
            @Mapping(source = "updated", target = "updated", dateFormat = DATE_FORMAT)
    })
    PostDto toDto(Post post);

    @Mappings({
            @Mapping(target = "created", source = "created", ignore = true),
            @Mapping(target = "updated", source = "updated", ignore = true)
    })
    Post toEntity(PostDto postDto);
}