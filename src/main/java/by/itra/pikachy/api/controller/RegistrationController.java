package by.itra.pikachy.api.controller;

import by.itra.pikachy.api.dto.UserDto;
import by.itra.pikachy.api.entity.User;
import by.itra.pikachy.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}