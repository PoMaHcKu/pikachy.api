package by.itra.pikachy.api.service;

import by.itra.pikachy.api.dto.UserDto;
import by.itra.pikachy.api.entity.User;
import by.itra.pikachy.api.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final UserMapper userMapper;

    @Transactional
    public UserDto verifyAndCleanToken(String token) {
        User user = userService.getByVerificationToken(token);
        if (user == null) {
            throw new RuntimeException("Token: " + token + " not found.");
        }
        user.setEnabled(true);
        user.setVerificationToken("");
        return userMapper.toDto(userService.update(user));
    }

    public String generateToken() {
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
}