package by.itra.pikachy.api.security;

import by.itra.pikachy.api.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsFactory {
    public static UserDetails createUserDetails(User user) {
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setUsername(user.getUsername());
        userDetails.setPassword(user.getPassword());
        userDetails.setRoles(convertToGrantedAuthority(user));
        userDetails.setEnabled(user.isEnabled());
        return userDetails;
    }

    private static List<GrantedAuthority> convertToGrantedAuthority(User user) {
        return user.getRoles().stream().map(x ->
                new SimpleGrantedAuthority(x.getRoleName())).collect(Collectors.toList());
    }
}
