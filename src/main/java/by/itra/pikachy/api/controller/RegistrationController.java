package by.itra.pikachy.api.controller;

import by.itra.pikachy.api.dto.AuthRequestDto;
import by.itra.pikachy.api.dto.AuthResponseDto;
import by.itra.pikachy.api.dto.UserDto;
import by.itra.pikachy.api.entity.User;
import by.itra.pikachy.api.service.AuthenticationService;
import by.itra.pikachy.api.service.RegistrationService;
import by.itra.pikachy.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;
    private final AuthenticationService authService;
    private final RegistrationService registrationService;

    @PostMapping("/registration")
    public ResponseEntity<UserDto> registration(@RequestBody @Valid User user) {
        return new ResponseEntity<>(userService.created(user), HttpStatus.OK);
    }

    @GetMapping("/registration/{token}")
    public UserDto confirmRegistration(@PathVariable String token) {
        return registrationService.verifyAndCleanToken(token);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authDto) {
        return authService.login(authDto);
    }
}