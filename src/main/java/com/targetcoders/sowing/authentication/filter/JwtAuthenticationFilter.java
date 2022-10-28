package com.targetcoders.sowing.authentication.filter;

import com.targetcoders.sowing.member.MemberRole;
import com.targetcoders.sowing.authentication.service.HeaderSetService;
import com.targetcoders.sowing.authentication.domain.JwtParserService;
import com.targetcoders.sowing.authentication.domain.JwtTokenProvider;
import com.targetcoders.sowing.authentication.service.TokenFindService;
import com.targetcoders.sowing.authentication.exception.InvalidTokenException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtParserService jwtParserService;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenFindService tokenFindService;
    private final HeaderSetService headerSetService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String oldAccessToken = jwtTokenProvider.getAccessToken((HttpServletRequest) servletRequest);
        if (isPassRequest(request, oldAccessToken)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (oldAccessToken.equals("")) {
            throw new InvalidTokenException("Access token is invalid.");
        }

        String userPk = userPk(oldAccessToken);
        String refreshToken = tokenFindService.sowingRefreshToken(userPk);
        if (!jwtParserService.refreshToken(oldAccessToken).equals(refreshToken)){
            throw new InvalidTokenException("This refresh token is disabled.");
        }

        if (isValidJwt(oldAccessToken)) {
            setNewAccessToken(refreshToken, (HttpServletResponse) servletResponse);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (isValidJwt(refreshToken)) {
            setNewAccessToken(refreshToken, (HttpServletResponse) servletResponse);
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private String userPk(String oldAccessToken) {
        String userPk;
        try {
            userPk = jwtParserService.userPk(oldAccessToken);
        } catch (JwtException e) {
            throw new InvalidTokenException("JWT is invalid.");
        }
        return userPk;
    }

    private void setNewAccessToken(String refreshToken, HttpServletResponse servletResponse) {
        String newAccessToken = accessToken(refreshToken);
        headerSetService.setAccessTokenCookie(servletResponse, newAccessToken, "900");
        SecurityContextHolder.getContext()
                .setAuthentication(authentication(newAccessToken));
    }

    private boolean isPassRequest(HttpServletRequest request, String accessToken) {
        return (accessToken.equals("") && request.getRequestURI().equals("/")) ||
                (accessToken.equals("") && request.getRequestURI().startsWith("/css/")) ||
                (accessToken.equals("") && request.getRequestURI().startsWith("/js/")) ||
                (accessToken.equals("") && request.getRequestURI().startsWith("/login/google"));
    }

    private boolean isValidJwt(String jwt) {
        return jwt != null && jwtParserService.isValidateToken(jwt);
    }

    private Authentication authentication(String accessToken) {
        return jwtTokenProvider.authentication(accessToken);
    }

    private String accessToken(String refreshToken) {
        String userPk = jwtParserService.userPk(refreshToken);
        MemberRole role = MemberRole.valueOf(jwtParserService.memberRole(refreshToken));
        return jwtTokenProvider.createAccessToken(userPk, role, refreshToken);
    }
}