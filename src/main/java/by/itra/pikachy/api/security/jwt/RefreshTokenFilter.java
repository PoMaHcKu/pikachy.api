package by.itra.pikachy.api.security.jwt;

import by.itra.pikachy.api.entity.AdditionalCredentials;
import by.itra.pikachy.api.service.AdditionalCredentialsService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RefreshTokenFilter implements Filter {

    private final JwtProvider jwtProvider;
    private final AdditionalCredentialsService credentialsService;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        List<AdditionalCredentials> credentials = credentialsService.getExpiredCredentials(new Date());
        clearExpiredCredentials(credentials);
        String refreshToken = req.getHeader("Refresh");
        try {
            if (jwtProvider.validateRefreshToken(refreshToken)) {
                Authentication authentication = jwtProvider.getAuthentication(refreshToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtException e) {
            e.printStackTrace();
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Bean
    @Autowired
    public FilterRegistrationBean<RefreshTokenFilter> refreshFilter(JwtProvider provider,
                                                                    AdditionalCredentialsService service) {
        FilterRegistrationBean<RefreshTokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RefreshTokenFilter(provider, service));
        registrationBean.addUrlPatterns("/login/refresh");
        return registrationBean;
    }

    private void clearExpiredCredentials(List<AdditionalCredentials> credentials) {
        for (AdditionalCredentials credential : credentials) {
            credentialsService.remove(credential);
        }
    }
}