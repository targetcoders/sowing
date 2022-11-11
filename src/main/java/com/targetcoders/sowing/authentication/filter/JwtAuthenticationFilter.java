package com.targetcoders.sowing.authentication.filter;

import com.targetcoders.sowing.authentication.domain.JwtToken;
import com.targetcoders.sowing.authentication.exception.InvalidTokenException;
import com.targetcoders.sowing.authentication.service.HeaderSetService;
import com.targetcoders.sowing.authentication.service.JwtTokenService;
import com.targetcoders.sowing.authentication.service.TokenFindService;
import com.targetcoders.sowing.member.domain.MemberRole;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final UserDetailsService userDetailsService;
    private final TokenFindService tokenFindService;
    private final HeaderSetService headerSetService;
    private final JwtParser jwtParser;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        JwtToken oldAccessToken = jwtTokenService.getAccessToken(request);

        if (isPassRequest(request, oldAccessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        String userPk = userPk(oldAccessToken);
        JwtToken refreshToken = tokenFindService.sowingRefreshToken(userPk);
        if (!oldAccessToken.refreshToken(jwtParser).equals(refreshToken)){
            throw new InvalidTokenException("This refresh token is disabled.");
        }

        if (oldAccessToken.isValidToken(jwtParser)) {
            setNewAccessToken(refreshToken, response);
            filterChain.doFilter(request, response);
            return;
        }

        if (refreshToken.isValidToken(jwtParser)) {
            setNewAccessToken(refreshToken, response);
            filterChain.doFilter(request, response);
        }
    }

    private String userPk(JwtToken oldAccessToken) {
        String userPk;
        try {
            userPk = oldAccessToken.userPk(jwtParser);
        } catch(NullPointerException | JwtException e) {
            throw new InvalidTokenException("Fail to get userPk from access token. message="+e.getMessage());
        }
        return userPk;
    }

    private void setNewAccessToken(JwtToken refreshToken, HttpServletResponse servletResponse) {
        JwtToken newAccessToken = accessToken(refreshToken);
        headerSetService.setAccessTokenCookie(servletResponse, newAccessToken.toString(), "900");
        SecurityContextHolder.getContext()
                .setAuthentication(authentication(newAccessToken));
    }

    private boolean isPassRequest(HttpServletRequest request, JwtToken accessToken) {
        return (accessToken == null && request.getRequestURI().equals("/")) ||
                (accessToken == null && request.getRequestURI().startsWith("/login/google")) ||
                request.getRequestURI().startsWith("/css/") ||
                request.getRequestURI().startsWith("/js/") ||
                request.getRequestURI().startsWith("/images/") ||
                request.getRequestURI().startsWith("/error");
    }

    private Authentication authentication(JwtToken accessToken) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(accessToken.userPk(jwtParser));
        return new UsernamePasswordAuthenticationToken(userDetails, accessToken.toString(), userDetails.getAuthorities());
    }

    private JwtToken accessToken(JwtToken refreshToken) {
        String userPk = refreshToken.userPk(jwtParser);
        MemberRole role = MemberRole.valueOf(refreshToken.memberRole(jwtParser));
        return jwtTokenService.createAccessToken(userPk, role, refreshToken);
    }

}