package by.itra.pikachy.api.mapper;

import by.itra.pikachy.api.dto.UserDto;
import by.itra.pikachy.api.entity.Role;
import by.itra.pikachy.api.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = PostMapper.class)
public interface UserMapper {

    String DATE_FORMAT = "HH:mm dd.MM.yyyy";

    @Mappings({
            @Mapping(source = "created", target = "created", dateFormat = DATE_FORMAT),
            @Mapping(source = "lastLogin", target = "lastLogin", dateFormat = DATE_FORMAT)
    })
    UserDto toDto(User model);

    User toEntity(UserDto dto);

    default String fromRole(Role role) {
        return role.getRoleName();
    }

    default Role toRole(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);
        return role;
    }
}