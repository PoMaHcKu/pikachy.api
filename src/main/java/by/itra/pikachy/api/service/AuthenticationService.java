package by.itra.pikachy.api.service;

import by.itra.pikachy.api.dto.AuthRequestDto;
import by.itra.pikachy.api.dto.AuthResponseDto;
import by.itra.pikachy.api.entity.AdditionalCredentials;
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
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final AdditionalCredentialsService credentialsService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Transactional
    public ResponseEntity<AuthResponseDto> login(AuthRequestDto authDto, HttpServletRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authDto.getUsername(),
                            authDto.getPassword())
            );
            User user = userService.findByUsername(authDto.getUsername());
            if (user == null) {
                throw new UsernameNotFoundException("Username not found");
            }
            ResponseEntity<AuthResponseDto> responseEntity = new ResponseEntity<>(
                    preparedResponse(user, request),
                    HttpStatus.OK);
            AdditionalCredentials credentials = jwtProvider.getAddCredentials(responseEntity.getBody().getRefreshToken());
            credentials.setUser(user);
            credentialsService.save(credentials);
            return responseEntity;
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    public ResponseEntity<AuthResponseDto> refreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("Refresh");
        User user = userService.findByUsername(jwtProvider.getUsername(refreshToken));
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        return new ResponseEntity<>(
                preparedResponse(user, request),
                HttpStatus.OK
        );
    }

    public User getAuthenticatedUser(Principal user) {
        return userService.findByUsername(user.getName());
    }

    private AuthResponseDto preparedResponse(User user, HttpServletRequest request) {
        return AuthResponseDto.builder()
                .username(user.getUsername())
                .token(jwtProvider.generateToken(user.getUsername(), user.getRoles()))
                .refreshToken(jwtProvider.generateRefreshToken(
                        user.getUsername(),
                        request.getHeader("user-agent"),
                        user.getRoles()))
                .build();
    }
}