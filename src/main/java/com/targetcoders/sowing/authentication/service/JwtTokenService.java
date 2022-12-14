package com.targetcoders.sowing.authentication.service;

import com.targetcoders.sowing.authentication.domain.JwtToken;
import com.targetcoders.sowing.member.domain.MemberRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenService {

    @Value("${jwt.accessTokenValidMillisecond}")
    private Long ACCESS_TOKEN_VALID_MILLISECOND;
    @Value("${jwt.refreshTokenValidMillisecond}")
    private Long REFRESH_TOKEN_VALID_MILLISECOND;

    private final SecretKey secretKey;
    private final IDate date;

    public JwtToken createAccessToken(String userPk, MemberRole role, JwtToken refreshToken) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("role", role);
        claims.put("rt", refreshToken.toString());

        String jwtToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date.instance())
                .setExpiration(new Date(date.nowTime() + ACCESS_TOKEN_VALID_MILLISECOND))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
        return new JwtToken(jwtToken);
    }

    public JwtToken createRefreshToken(String userPk, MemberRole role) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("role", role);

        String jwtToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date.instance())
                .setExpiration(new Date(date.nowTime() + REFRESH_TOKEN_VALID_MILLISECOND))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
        return new JwtToken(jwtToken);
    }

    public JwtToken getAccessToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("ACCESS-TOKEN")) {
                    if (cookie.getValue().equals("")) {
                        return null;
                    }
                    return new JwtToken(cookie.getValue());
                }
            }
        }
        return null;
    }

}