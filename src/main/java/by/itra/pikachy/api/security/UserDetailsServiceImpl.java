package by.itra.pikachy.api.security;

import by.itra.pikachy.api.entity.User;
import by.itra.pikachy.api.service.UserService;
import by.itra.pikachy.api.util.GetDate;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + username + "not found.");
        }
        updateLastLogin(user);
        return UserDetailsFactory.createUserDetails(user);
    }

    private void updateLastLogin(User user) {
        user.setLastLogin(GetDate.getLocalDate());
        userService.update(user);
    }
}