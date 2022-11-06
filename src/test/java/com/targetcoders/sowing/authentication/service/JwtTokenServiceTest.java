package com.targetcoders.sowing.authentication.service;

import com.targetcoders.sowing.authentication.domain.JwtToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.Cookie;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class JwtTokenServiceTest {

    @Autowired private JwtTokenService jwtTokenService;

    @Test
    @DisplayName("ACCESS-TOKEN이 empty string인 경우 null을 반환한다.")
    void emptyAccessToken() {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        Cookie[] cookies = new Cookie[1];
        Cookie cookie = new Cookie("ACCESS-TOKEN", "");
        cookies[0] = cookie;
        mockHttpServletRequest.setCookies(cookies);

        JwtToken jwtToken = jwtTokenService.getAccessToken(mockHttpServletRequest);

        assertThat(jwtToken).isNull();
    }

    @Test
    @DisplayName("ACCESS-TOKEN이 없는 경우 null을 반환한다.")
    void noAccessToken() {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

        JwtToken jwtToken = jwtTokenService.getAccessToken(mockHttpServletRequest);

        assertThat(jwtToken).isNull();
    }

    @Test
    @DisplayName("유효한 형식의 ACCESS-TOKEN인 경우 JwtToken을 반환한다.")
    void formattedAccessToken() {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        Cookie[] cookies = new Cookie[1];
        Cookie cookie = new Cookie("ACCESS-TOKEN", "a.b.c");
        cookies[0] = cookie;
        mockHttpServletRequest.setCookies(cookies);

        JwtToken jwtToken = jwtTokenService.getAccessToken(mockHttpServletRequest);

        assertThat(jwtToken).isEqualTo(new JwtToken("a.b.c"));
    }

    @Test
    @DisplayName("유효하지 않은 형식의 ACCESS-TOKEN인 경우 IllegalArgumentException이 발생한다.")
    void malFormattedAccessToken() {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        Cookie[] cookies = new Cookie[1];
        Cookie cookie = new Cookie("ACCESS-TOKEN", "MalFormattedAccessToken");
        cookies[0] = cookie;
        mockHttpServletRequest.setCookies(cookies);

        Assertions.assertThrows(IllegalArgumentException.class, ()->jwtTokenService.getAccessToken(mockHttpServletRequest));
    }

}