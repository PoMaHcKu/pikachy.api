package by.itra.pikachy.api.controller;

import by.itra.pikachy.api.dto.UserDto;
import by.itra.pikachy.api.entity.User;
import by.itra.pikachy.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;


    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto registration(@RequestBody User user) {
        return userService.created(user);
    }


    @GetMapping("{token}")
    public UserDto confirmRegistration(@PathVariable String token) {
       return userService.verifyAndCleanToken(token);
    }

    @GetMapping("login")
    public UserDto getAuthenticate(@AuthenticationPrincipal User user) {
        return userService.authenticate(user);
    }
    @GetMapping("/logout")
    public void deleteAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
    }
}