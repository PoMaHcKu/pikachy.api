package by.itra.pikachy.api.security;

import by.itra.pikachy.api.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsFactory {
    public static UserDetailsImpl createUserDetails(User user) {
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setId(user.getId());
        userDetails.setUsername(user.getUsername());
        userDetails.setPassword(user.getPassword());
        userDetails.setRoles(convertToGrantedAuthority(user));
        userDetails.setEnabled(user.isEnabled());
        userDetails.setCredentials(user.getAdditionalCredentials());
        return userDetails;
    }

    private static List<GrantedAuthority> convertToGrantedAuthority(User user) {
        return user.getRoles().stream().map(x ->
                new SimpleGrantedAuthority(x.getRoleName())).collect(Collectors.toList());
    }
}