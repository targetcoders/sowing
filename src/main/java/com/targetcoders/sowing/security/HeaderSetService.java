package com.targetcoders.sowing.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Service
public class HeaderSetService {

    public void setAccessTokenCookie(HttpServletResponse response, String newAccessToken, String maxAge) {
        response.setHeader("Set-Cookie", "ACCESS-TOKEN=" + newAccessToken + "; path=/; max-age="+maxAge+"; HttpOnly; SameSite=Lax");
    }

}
