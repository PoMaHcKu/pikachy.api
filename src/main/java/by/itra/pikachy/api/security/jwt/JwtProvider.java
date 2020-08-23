package by.itra.pikachy.api.security.jwt;

import by.itra.pikachy.api.entity.AdditionalCredentials;
import by.itra.pikachy.api.entity.Role;
import by.itra.pikachy.api.exception.JwtAuthenticationException;
import by.itra.pikachy.api.security.UserDetailsImpl;
import by.itra.pikachy.api.security.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    public static final String AUTHORIZATION = "Authorization";
    private UserDetailsServiceImpl userDetailsService;
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expired}")
    private long expired;

    public String generateToken(String username, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", getRoleNames(roles));
        Date now = new Date();
        Date validity = new Date(now.getTime() + expired);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String generateRefreshToken(String username, String userAgent, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("userAgent", userAgent);
        claims.put("roles", getRoleNames(roles));
        Date now = new Date();
        Date validity = new Date(now.getTime() + 1000000L);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        if (token != null && token.startsWith("Bearer")) {
            return token.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("Jwt token is expired or invalid", e);
        }
    }

    public boolean validateRefreshToken(String token) {
        UserDetailsImpl credentials = userDetailsService.loadUserByUsername(getUsername(token));
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            if (claimsJws.getBody().getExpiration().before(new Date())) {
                return false;
            }
            for (AdditionalCredentials i : credentials.getCredentials()) {
                if (i.getUserAgent().equals(claimsJws.getBody().get("userAgent"))) {
                    return true;
                }
            }
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("Refresh token isn't valid", e);
        }
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public AdditionalCredentials getAddCredentials(String refreshToken) {
        AdditionalCredentials credentials = new AdditionalCredentials();
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(refreshToken);
        credentials.setExpiration(claimsJws.getBody().getExpiration());
        credentials.setUserAgent(claimsJws.getBody().get("userAgent").toString());
        return credentials;
    }

    private List<String> getRoleNames(List<Role> roles) {
        return roles.stream().map(Role::getRoleName).collect(Collectors.toList());
    }

    @Autowired
    public void setUserDetailsService(@Lazy UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}