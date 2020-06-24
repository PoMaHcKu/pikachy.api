package by.itra.pikachy.api.mapper;

import by.itra.pikachy.api.dto.PostDto;
import by.itra.pikachy.api.entity.Post;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    PostMapper POST_MAPPER = Mappers.getMapper(PostMapper.class);
    PostDto fromPost(Post post);
    @InheritInverseConfiguration
    Post toPost(PostDto postDto);
}