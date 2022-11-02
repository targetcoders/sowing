package com.targetcoders.sowing.authentication.service;

import com.targetcoders.sowing.authentication.domain.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Service
public class HeaderSetService {

    public void setAccessTokenCookie(HttpServletResponse response, JwtToken newAccessToken, String maxAge) {
        response.setHeader("Set-Cookie", "ACCESS-TOKEN=" + newAccessToken + "; path=/; max-age="+maxAge+"; HttpOnly; SameSite=Lax");
    }

}
