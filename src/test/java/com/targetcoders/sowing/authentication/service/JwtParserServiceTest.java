package com.targetcoders.sowing.authentication.service;

import com.targetcoders.sowing.member.MemberRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class JwtParserServiceTest {

    public static final String USER_PK = "greenneuron";
    @Autowired
    private JwtParserService jwtParserService;
    @Autowired
    private JwtTokenService jwtTokenService;

    private String accessToken;
    private String refreshToken;

    @BeforeEach
    void init() {
        refreshToken = jwtTokenService.createRefreshToken(USER_PK, MemberRole.ROLE_USER);
        accessToken = jwtTokenService.createAccessToken(USER_PK, MemberRole.ROLE_USER, refreshToken);
    }

    @Test
    @DisplayName("jwt에서 Claims를 추출")
    void parseClaims() {
        //when
        String role = jwtParserService.memberRole(accessToken);
        String userPk = jwtParserService.userPk(accessToken);
        String rt = jwtParserService.refreshToken(accessToken);

        //then
        assertThat(role).isEqualTo(MemberRole.ROLE_USER.toString());
        assertThat(userPk).isEqualTo(USER_PK);
        assertThat(rt).isEqualTo(refreshToken);
    }
}