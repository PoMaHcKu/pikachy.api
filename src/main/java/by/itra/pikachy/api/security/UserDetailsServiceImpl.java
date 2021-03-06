package by.itra.pikachy.api.security;

import by.itra.pikachy.api.entity.User;
import by.itra.pikachy.api.service.UserService;
import by.itra.pikachy.api.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
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