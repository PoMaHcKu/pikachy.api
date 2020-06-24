package by.itra.pikachy.api.service;

import by.itra.pikachy.api.dto.UserDto;
import by.itra.pikachy.api.entity.Role;
import by.itra.pikachy.api.entity.User;
import by.itra.pikachy.api.mapper.UserMapper;
import by.itra.pikachy.api.repository.RoleRepository;
import by.itra.pikachy.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final String USER_ROLE = "USER_ROLE";
    @Value("${mail.text}")
    private String textMessage;
    @Value("${app.address}")
    private String appAddress;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public UserDto findByUsername(String username) {
        return UserMapper.USER_MAPPER.fromUser(userRepository.findByUsername(username));
    }

    @Transactional
    public User created(User user) {
        Role userRole = roleRepository.findByRoleName(USER_ROLE);
        user.getRoles().add(userRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVerificationToken(generateToken());
        User registeredUser = userRepository.save(user);
        emailService.sendSimpleMessage(
                user.getEmail(),
                getTextMessage(registeredUser.getVerificationToken()));
        return userRepository.save(user);
    }

    @Transactional
    public User verifyAndCleanToken(String token) {
        User user = userRepository.findByVerificationToken(token);
        if (user == null) {
            return null;
        }
        user.setEnabled(true);
        user.setVerificationToken("");
        return userRepository.save(user);
    }

    private String generateToken() {
        byte[] bytes = new SecureRandom().generateSeed(16);
        return bytesToHex(bytes);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(Integer.toHexString(0xFF & b));
        }
        return hexString.toString();
    }

    private String getTextMessage(String token) {
        return textMessage + "\n" + getConfirmLink(token);
    }

    private String getConfirmLink(String token) {
        return appAddress + token;
    }
}