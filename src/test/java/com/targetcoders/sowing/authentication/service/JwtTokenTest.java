package com.targetcoders.sowing.authentication.service;

import com.targetcoders.sowing.authentication.domain.JwtToken;
import com.targetcoders.sowing.member.MemberRole;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class JwtTokenTest {

    public static final String USER_PK = "greenneuron";
    @Autowired
    private JwtParser jwtParser;
    @Autowired
    private JwtTokenService jwtTokenService;
    @MockBean
    private IDate date;


    @Test
    @DisplayName("잘못된 JwtToken 생성시 IllegalArgumentException이 발생한다")
    void constructor() {
        assertThrows(IllegalArgumentException.class, ()-> new JwtToken(".."));
        assertThrows(IllegalArgumentException.class, ()-> new JwtToken(""));
        assertThrows(IllegalArgumentException.class, ()-> new JwtToken("test token"));
        assertThrows(IllegalArgumentException.class, ()-> new JwtToken("1-3-4"));
        assertThrows(IllegalArgumentException.class, ()-> new JwtToken("12asdf3.asdfsa324"));
        assertThrows(IllegalArgumentException.class, ()-> new JwtToken("12asdf3.asdfsa324.11111.cvzasd"));
        assertDoesNotThrow(()->{new JwtToken("a.b.c");});
    }

    @Test
    @DisplayName("jwt 토큰에서 Claims를 추출한다")
    void parseClaims() {
        //given
        Mockito.when(date.nowTime()).thenReturn(new Date().getTime());
        JwtToken refreshToken = jwtTokenService.createRefreshToken(USER_PK, MemberRole.ROLE_USER);
        JwtToken accessToken = jwtTokenService.createAccessToken(USER_PK, MemberRole.ROLE_USER, refreshToken);

        //when
        String role = accessToken.memberRole(jwtParser);
        String userPk = accessToken.userPk(jwtParser);
        JwtToken rt = accessToken.refreshToken(jwtParser);

        //then
        assertThat(role).isEqualTo(MemberRole.ROLE_USER.toString());
        assertThat(userPk).isEqualTo(USER_PK);
        assertThat(rt).isEqualTo(refreshToken);
    }

    @Test
    @DisplayName("유효한 토큰인 것을 확인한다")
    void isValidToken() {
        //given
        Mockito.when(date.nowTime()).thenReturn(new Date().getTime());
        JwtToken refreshToken = jwtTokenService.createRefreshToken(USER_PK, MemberRole.ROLE_USER);

        //when
        boolean result = refreshToken.isValidToken(jwtParser);

        //then
        assertTrue(result);
    }

    @Test
    @DisplayName("basicToken은 유효한 토큰이 아니다")
    void basicToken() {
        //given
        Mockito.when(date.instance()).thenReturn(new Date(0));
        JwtToken basicToken = new JwtToken("a.b.c");

        //when
        boolean isValidToken = basicToken.isValidToken(jwtParser);

        //then
        assertThat(isValidToken).isEqualTo(false);
    }

    @Test
    @DisplayName("Claims 추출 시 토큰 형식이 틀리면 MalformedJwtException이 발생한다")
    void failGetClaims() {
        //given
        Mockito.when(date.instance()).thenReturn(new Date(0));
        JwtToken basicToken = new JwtToken("a.b.c");

        //assert
        assertThrows(MalformedJwtException.class, ()-> basicToken.userPk(jwtParser));
        assertThrows(MalformedJwtException.class, ()-> basicToken.refreshToken(jwtParser));
        assertThrows(MalformedJwtException.class, ()-> basicToken.memberRole(jwtParser));
    }

    @Test
    @DisplayName("Claims 추출 시 토큰이 만료되었으면 ExpiredJwtException이 발생한다")
    void expiredToken() {
        //given
        Mockito.when(date.instance()).thenReturn(new Date(0));

        //when
        JwtToken refreshToken = jwtTokenService.createRefreshToken("greenneuron", MemberRole.ROLE_USER);

        //then
        assertThrows(ExpiredJwtException.class, ()-> refreshToken.memberRole(jwtParser));
        assertThrows(ExpiredJwtException.class, ()-> refreshToken.userPk(jwtParser));
        assertThrows(ExpiredJwtException.class, ()-> refreshToken.refreshToken(jwtParser));
        assertFalse(refreshToken.isValidToken(jwtParser));
    }
}