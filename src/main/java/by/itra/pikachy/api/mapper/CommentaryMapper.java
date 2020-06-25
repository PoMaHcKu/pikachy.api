package by.itra.pikachy.api.mapper;

import by.itra.pikachy.api.dto.CommentaryDto;
import by.itra.pikachy.api.entity.Commentary;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = PostMapper.class)
public interface CommentaryMapper {
    @Mappings({
            @Mapping(target = "created", source = "created", dateFormat = "hh:mm dd.MM.yy"),
    })
    CommentaryDto fromCommentary(Commentary commentary);

    @IterableMapping(dateFormat = "hh:mm dd.MM.yyyy")
    Commentary toCommentary(CommentaryDto commentaryDto);
}