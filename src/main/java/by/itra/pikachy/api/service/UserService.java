package by.itra.pikachy.api.service;

import by.itra.pikachy.api.dto.UserDto;
import by.itra.pikachy.api.entity.User;
import by.itra.pikachy.api.mapper.UserMapper;
import by.itra.pikachy.api.repository.RoleRepository;
import by.itra.pikachy.api.repository.UserRepository;
import by.itra.pikachy.api.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final UserMapper userMapper;
    private final String USER_ROLE = "ROLE_USER";
    @Value("${mail.text}")
    private String textMessage;
    @Value("${app.address}")
    private String appAddress;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       EmailService emailService,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.userMapper = userMapper;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public UserDto created(User user) {
        user.getRoles().add(roleRepository.findByRoleName(USER_ROLE));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVerificationToken(generateToken());
        sendTokenOnEmail(userRepository.save(user));
        user.setCreated(GetDate.getLocalDate());
        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional
    public UserDto verifyAndCleanToken(String token) {
        User user = userRepository.findByVerificationToken(token);
        if (user == null) {
            throw new RuntimeException("Token: " + token + " not found.");
        }
        user.setEnabled(true);
        user.setVerificationToken("");
        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional
    public UserDto getUserById(int userId) {
        return userMapper.toDto(userRepository.getOne(userId));
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    private String generateToken() {
        byte[] bytes = new SecureRandom().generateSeed(16);
        return bytesToHex(bytes);
    }

    public UserDto signIn() {
        return userMapper.toDto(getAuthenticatedUser(SecurityContextHolder.getContext()));
    }

    public User getAuthenticatedUser(SecurityContext context) {
        UserDetails userDetails = (UserDetails) context.getAuthentication().getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername());
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public Page<User> getPage(int numberPage, int size) {
        Pageable pageable = PageRequest.of(numberPage, size, Sort.by(
                Sort.Order.desc("created"),
                Sort.Order.asc("username")));
        return userRepository.findAll(pageable);
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

    private void sendTokenOnEmail(User user) {
        emailService.sendSimpleMessage(
                user.getEmail(),
                getTextMessage(user.getVerificationToken()));
    }
}