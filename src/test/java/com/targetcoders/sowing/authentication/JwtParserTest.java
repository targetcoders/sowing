package com.targetcoders.sowing.authentication;

import com.targetcoders.sowing.authentication.domain.JwtToken;
import com.targetcoders.sowing.authentication.service.IDate;
import com.targetcoders.sowing.authentication.service.JwtTokenService;
import com.targetcoders.sowing.member.domain.MemberRole;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class JwtParserTest {

    @Autowired JwtParser jwtParser;
    @Autowired JwtTokenService jwtTokenService;
    @MockBean IDate date;

    @Test
    @DisplayName("유효 기간이 만료된 Jwt 토큰을 파싱 시도할 경우 ExpiredJwtException이 발생한다.")
    void parseExpiredJwtToken() {
        Mockito.when(date.nowTime()).thenReturn(new Date(0).getTime());
        String email = "greenneuron@email.com";
        JwtToken oldJwtToken = jwtTokenService.createRefreshToken(email, MemberRole.ROLE_USER);

        Assertions.assertThrows(ExpiredJwtException.class, ()->jwtParser.parseClaimsJws(oldJwtToken.toString()));
    }

    @Test
    @DisplayName("유효하지 않은 형식의 Jwt 토큰을 파싱 시도할 경우 MalformedJwtException이 발생한다.")
    void parseMalFormattedJwtToken() {
        JwtToken malformedJwtToken = new JwtToken("a.b.c");

        Assertions.assertThrows(MalformedJwtException.class, ()->jwtParser.parseClaimsJws(malformedJwtToken.toString()));
    }

    @Test
    @DisplayName("null 또는 empty string을 파싱 시도할 경우 IllegalArgumentException이 발생한다.")
    void parseNullOrEmptyString() {
        Assertions.assertThrows(IllegalArgumentException.class, ()->jwtParser.parseClaimsJws(null));
        Assertions.assertThrows(IllegalArgumentException.class, ()->jwtParser.parseClaimsJws(""));
    }
}