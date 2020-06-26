package by.itra.pikachy.api.mapper;

import by.itra.pikachy.api.dto.UserDto;
import by.itra.pikachy.api.entity.Role;
import by.itra.pikachy.api.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = PostMapper.class)
public interface UserMapper {

    User toEntity(UserDto dto);
    UserDto toDto(User model);

    default String fromRole(Role role) {
        return role.getRoleName();
    }

    default Role toRole(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);
        return role;
    }
}