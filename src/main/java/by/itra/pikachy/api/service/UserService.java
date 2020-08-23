package by.itra.pikachy.api.service;

import by.itra.pikachy.api.dto.UserDto;
import by.itra.pikachy.api.entity.User;
import by.itra.pikachy.api.mapper.UserMapper;
import by.itra.pikachy.api.repository.RoleRepository;
import by.itra.pikachy.api.repository.UserRepository;
import by.itra.pikachy.api.util.GetDate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final UserMapper userMapper;
    private RegistrationService registrationService;
    @Value("${mail.text}")
    private String textMessage;
    @Value("${app.address}")
    private String appAddress;

    @Autowired
    public void setRegistrationService(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public UserDto created(User user) {
        String USER_ROLE = "ROLE_USER";
        user.getRoles().add(roleRepository.findByRoleName(USER_ROLE));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVerificationToken(registrationService.generateToken());
        sendTokenOnEmail(userRepository.save(user));
        user.setCreated(GetDate.getLocalDate());
        return userMapper.toDto(userRepository.save(user));
    }

    public User getByVerificationToken(String token) {
        return userRepository.findByVerificationToken(token);
    }

    @Transactional
    public UserDto getUserById(int userId) {
        return userMapper.toDto(userRepository.getOne(userId));
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public Page<UserDto> getPage(int numberPage, int size) {
        Pageable pageable = PageRequest.of(numberPage, size, Sort.by(
                Sort.Order.desc("created"),
                Sort.Order.asc("username")));
        return userRepository.findAll(pageable).map(userMapper::toDto);
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