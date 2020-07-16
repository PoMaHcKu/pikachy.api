package by.itra.pikachy.api.controller;

import by.itra.pikachy.api.dto.UserDto;
import by.itra.pikachy.api.entity.User;
import by.itra.pikachy.api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@AllArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @PostMapping("/registration")
    public UserDto registration(@RequestBody @Valid User user) {
        return userService.created(user);
    }

    @GetMapping("/registration/{token}")
    public UserDto confirmRegistration(@PathVariable String token) {
        return userService.verifyAndCleanToken(token);
    }

    @GetMapping("/login")
    public void login() {
    }

    @GetMapping("/auth-me")
    public UserDto isAuth(@AuthenticationPrincipal Principal user) {
        return userService.signIn(user);
    }

    @GetMapping("/sign-out")
    public void logout() {
    }
}