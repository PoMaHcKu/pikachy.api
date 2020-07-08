package by.itra.pikachy.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.Cookie;
import java.util.Collections;

import static jdk.nashorn.internal.runtime.PropertyDescriptor.GET;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().cors()
                .and()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/post/**", "/commentary/**", "/user/**", "/chat/commentaries/**", "/commentary-messaging/**", "/auth-me/**").permitAll()
                .mvcMatchers("/login", "/registration/**", "/search").permitAll()
                .mvcMatchers("/admin/**").hasAnyRole("ADMIN")
                .anyRequest().hasRole("USER")
                .and()
                .httpBasic()
                .and()
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/sign-out", "GET")));

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}