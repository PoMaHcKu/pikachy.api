package by.itra.pikachy.api.controller;

import by.itra.pikachy.api.dto.AuthRequestDto;
import by.itra.pikachy.api.dto.AuthResponseDto;
import by.itra.pikachy.api.dto.UserDto;
import by.itra.pikachy.api.entity.User;
import by.itra.pikachy.api.security.jwt.JwtProvider;
import by.itra.pikachy.api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@AllArgsConstructor
public class RegistrationController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping("/registration")
    public ResponseEntity<UserDto> registration(@RequestBody @Valid User user) {
        return new ResponseEntity<>(userService.created(user), HttpStatus.OK);
    }

    @GetMapping("/registration/{token}")
    public UserDto confirmRegistration(@PathVariable String token) {
        return userService.verifyAndCleanToken(token);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword()));
            User user = userService.findByUsername(authDto.getUsername());
            if (user == null) {
                throw new UsernameNotFoundException("Username not found");
            }
            return new ResponseEntity<>(
                    AuthResponseDto.builder()
                            .username(user.getUsername())
                            .token(jwtProvider.generateToken(user.getUsername(), user.getRoles()))
                            .build(),
                    HttpStatus.OK);
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @GetMapping("/auth-me")
    public ResponseEntity<UserDto> isAuth(@AuthenticationPrincipal Principal user) {
        return new ResponseEntity<>(userService.signIn(user), HttpStatus.OK);
    }

    @GetMapping("/sign-out")
    public void logout() {
    }
}