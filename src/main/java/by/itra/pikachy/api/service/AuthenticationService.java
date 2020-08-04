package by.itra.pikachy.api.service;

import by.itra.pikachy.api.dto.AuthRequestDto;
import by.itra.pikachy.api.dto.AuthResponseDto;
import by.itra.pikachy.api.entity.User;
import by.itra.pikachy.api.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public ResponseEntity<AuthResponseDto> login(AuthRequestDto authDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authDto.getUsername(),
                            authDto.getPassword())
            );
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

    public User getAuthenticatedUser(Principal user) {
        return userService.findByUsername(user.getName());
    }
}