package com.targetcoders.sowing.authentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Service
public class HeaderSetService {

    @Value("${jwt.accessTokenValidMillisecond}")
    private Long ACCESS_TOKEN_VALID_MILLISECOND;

    public void setAccessTokenCookie(HttpServletResponse response, String newAccessToken) {
        response.setHeader("Set-Cookie", "ACCESS-TOKEN=" + newAccessToken + "; path=/; max-age="+ACCESS_TOKEN_VALID_MILLISECOND/1000+"; HttpOnly; SameSite=Lax");
    }

    public void removeAccessTokenCookie(HttpServletResponse response) {
        response.setHeader("Set-Cookie", "ACCESS-TOKEN=; path=/; max-age=-1; HttpOnly; SameSite=Lax");
    }

}
