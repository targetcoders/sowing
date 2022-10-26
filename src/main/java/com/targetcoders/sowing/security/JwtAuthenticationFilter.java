package com.targetcoders.sowing.security;

import com.targetcoders.sowing.exception.InvalidAccessTokenException;
import com.targetcoders.sowing.member.MemberRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String refreshToken = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);
        if (isPassRequest(request, refreshToken)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String oldAccessToken = request.getHeader("Authorization");
        if (isValidJwt(oldAccessToken)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (isValidJwt(refreshToken)) {
            SecurityContextHolder.getContext()
                    .setAuthentication(authentication(refreshToken));
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        throw new InvalidAccessTokenException("Refresh token is invalid.");
    }

    private boolean isPassRequest(HttpServletRequest request, String refreshToken) {
        return (refreshToken.equals("") && request.getRequestURI().equals("/")) ||
                (refreshToken.equals("") && request.getRequestURI().startsWith("/login/google"));
    }

    private boolean isValidJwt(String jwt) {
        return jwt != null && jwtTokenProvider.isValidateToken(jwt);
    }

    private Authentication authentication(String refreshToken) {
        return jwtTokenProvider.authentication(accessToken(refreshToken));
    }

    private String accessToken(String refreshToken) {
        String userPk = jwtTokenProvider.getUserPk(refreshToken);
        MemberRole role = MemberRole.valueOf(jwtTokenProvider.getMemberRole(refreshToken));
        return jwtTokenProvider.createAccessToken(userPk, role);
    }
}