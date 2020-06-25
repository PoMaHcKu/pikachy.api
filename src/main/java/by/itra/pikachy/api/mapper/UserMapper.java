package by.itra.pikachy.api.mapper;

import by.itra.pikachy.api.dto.UserDto;
import by.itra.pikachy.api.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "username", source = "dto.username"),
            @Mapping(target = "email", source = "dto.email"),
            @Mapping(target = "id", source = "dto.id")
    })
    User toEntity(UserDto dto);
    UserDto toDto(User model);
}
