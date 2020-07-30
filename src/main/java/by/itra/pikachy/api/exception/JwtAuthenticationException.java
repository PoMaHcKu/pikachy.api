package by.itra.pikachy.api.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String s) {
        super(s);
    }
}