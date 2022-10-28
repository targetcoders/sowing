package com.targetcoders.sowing.security;

import com.targetcoders.sowing.exception.InvalidTokenException;
import com.targetcoders.sowing.member.MemberRole;
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
        System.out.println("refreshToken = " + refreshToken);
        System.out.println("refreshToken in old = " + jwtTokenProvider.getRefreshToken(oldAccessToken));
        if (!jwtTokenProvider.getRefreshToken(oldAccessToken).equals(refreshToken)){
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
            userPk = jwtTokenProvider.getUserPk(oldAccessToken);
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
        return jwt != null && jwtTokenProvider.isValidateToken(jwt);
    }

    private Authentication authentication(String accessToken) {
        return jwtTokenProvider.authentication(accessToken);
    }

    private String accessToken(String refreshToken) {
        String userPk = jwtTokenProvider.getUserPk(refreshToken);
        MemberRole role = MemberRole.valueOf(jwtTokenProvider.getMemberRole(refreshToken));
        return jwtTokenProvider.createAccessToken(userPk, role, refreshToken);
    }
}