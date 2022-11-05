package com.targetcoders.sowing.authentication.domain;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;

import java.util.Date;

public class JwtToken {

    private final String value;

    public JwtToken(String value) {
        if (value == null || value.equals("")) {
            throw new IllegalArgumentException("JwtToken cannot be null or empty.");
        }
        if (value.split("\\.").length != 3) {
            throw new IllegalArgumentException("JwtToken format is wrong.");
        }
        this.value = value;
    }

    public String userPk(JwtParser jwtParser) {
        return jwtParser.parseClaimsJws(value).getBody().getSubject();
    }

    public String memberRole(JwtParser jwtParser) {
        return (String) jwtParser.parseClaimsJws(value).getBody().get("role");
    }

    public JwtToken refreshToken(JwtParser jwtParser) {
        String refreshToken = (String) jwtParser.parseClaimsJws(value).getBody().get("rt");
        return new JwtToken(refreshToken);
    }

    public boolean isValidToken(JwtParser jwtParser) {
        try {
            Jws<Claims> claims = jwtParser.parseClaimsJws(value);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() != this.getClass()) {
            return false;
        }
        JwtToken another = (JwtToken) o;
        return value.equals(another.value);
    }

}
