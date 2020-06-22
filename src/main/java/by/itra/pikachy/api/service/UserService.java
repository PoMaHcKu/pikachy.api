package by.itra.pikachy.api.service;

import by.itra.pikachy.api.entity.Role;
import by.itra.pikachy.api.entity.User;
import by.itra.pikachy.api.repository.RoleRepository;
import by.itra.pikachy.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
public class UserService {

    private final String USER_ROLE = "USER_ROLE";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User created(User user) {
        Role userRole = roleRepository.findByRoleName(USER_ROLE);
        user.getRoles().add(userRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVerificationToken(generateToken());
        return userRepository.save(user);
    }

    private String generateToken() {
        byte[] bytes = new SecureRandom().generateSeed(16);
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            result.append(result.charAt(aByte));
        }
        return result.toString();
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public User verifyToken(String token) {
        User user = userRepository.findByVerificationToken(token);
        if (user == null) {
            throw new UsernameNotFoundException("This token isn't exist.");
        }
        user.setEnabled(true);
        user.setVerificationToken("");
        return userRepository.save(user);
    }

}
