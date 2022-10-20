package com.targetcoders.sowing.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final GoogleOauth2Manager googleOauth2Manager;

    public String jwtToken(String code) throws JsonProcessingException {
        String accessToken = googleOauth2Manager.googleAuthorization(code).getAccessToken();
        System.out.println("accessToken = " + accessToken);
        GoogleUserInfoDTO googleUserInfoDTO = googleOauth2Manager.googleUserInfo(accessToken);
        System.out.println("googleUserInfoDTO = " + googleUserInfoDTO);

        //TODO: 첫 방문인 경우 Member 등록 (회원가입)

        //TODO: jwt token 생성 및 반환

        return "jwtToken";
    }

}
