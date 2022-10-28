package com.targetcoders.sowing.authentication.domain;

import com.targetcoders.sowing.member.MemberRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.accessTokenValidMillisecond}")
    private Long ACCESS_TOKEN_VALID_MILLISECOND;
    @Value("${jwt.refreshTokenValidMillisecond}")
    private Long REFRESH_TOKEN_VALID_MILLISECOND;

    private final SecretKey secretKey;
    private final JwtParserService jwtParserService;
    private final UserDetailsService userDetailsService;

    public String createAccessToken(String userPk, MemberRole role, String refreshTokenPassword) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("role", role);
        claims.put("rt", refreshTokenPassword);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_MILLISECOND))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String userPk, MemberRole role) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("role", role);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_VALID_MILLISECOND))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication authentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtParserService.userPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    public String getAccessToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("ACCESS-TOKEN")) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }

}