package by.itra.pikachy.api.mapper;

import by.itra.pikachy.api.dto.UserDto;
import by.itra.pikachy.api.entity.Role;
import by.itra.pikachy.api.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = PostMapper.class)
public interface UserMapper {

    User toEntity(UserDto dto);
    UserDto toDto(User model);

    default List<String> roleToString(List<Role> roles) {
        return roles.stream().map(Role::getRoleName).collect(Collectors.toList());
    }

    default List<Role> stringToRoles(List<String> roles) {
        return roles.stream().map(r -> {
            Role role = new Role();
            role.setRoleName(r);
            return role;
        }).collect(Collectors.toList());
    }
}