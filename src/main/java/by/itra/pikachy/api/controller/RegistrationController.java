package by.itra.pikachy.api.controller;

import by.itra.pikachy.api.entity.User;
import by.itra.pikachy.api.mail.EmailService;
import by.itra.pikachy.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;
    private final EmailService emailService;
    @Value("${mail.text}")
    private String textMessage;
    @Value("${app.address}")
    private String appAddress;

    @Autowired
    public RegistrationController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping
    public boolean registration(@RequestBody User user) {
        User registeredUser = userService.created(user);
        emailService.sendSimpleMessage(user.getEmail(), getTextMessage(registeredUser.getVerificationToken()));
        return userService.updateUser(user) != null;
    }

    private String getTextMessage(String token) {
        return textMessage + "\n" + getConfirmLink(token);
    }

    private String getConfirmLink(String token) {
        return appAddress + token;
    }

    @GetMapping("{token}")
    public boolean confirmRegistration(@PathVariable String token) {
       return userService.verifyToken(token) != null;
    }
}
