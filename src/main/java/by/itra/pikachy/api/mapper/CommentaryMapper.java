package by.itra.pikachy.api.mapper;

import by.itra.pikachy.api.dto.CommentaryDto;
import by.itra.pikachy.api.dto.PostDto;
import by.itra.pikachy.api.entity.Commentary;
import by.itra.pikachy.api.entity.Post;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentaryMapper {
    CommentaryMapper COMMENTARY_MAPPER = Mappers.getMapper(CommentaryMapper.class);
    CommentaryDto fromCommentary(Commentary commentary);
    @InheritInverseConfiguration
    Commentary toCommentary(CommentaryDto commentaryDto);
}