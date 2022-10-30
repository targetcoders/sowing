package com.targetcoders.sowing.authentication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.targetcoders.sowing.authentication.dto.GoogleAuthorizationDTO;
import com.targetcoders.sowing.authentication.dto.GoogleUserInfoDTO;
import com.targetcoders.sowing.member.GoogleTokens;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleLoginServiceImpl implements GoogleLoginService{

    private final GoogleOauth2Service googleOauth2Service;

    public GoogleTokens googleTokens(String code) throws JsonProcessingException {
        GoogleAuthorizationDTO googleAuthorizationDTO = googleOauth2Service.googleAuthorization(code);
        String accessToken = googleAuthorizationDTO.getAccessToken();
        String refreshToken = googleAuthorizationDTO.getRefreshToken();
        return new GoogleTokens(accessToken, refreshToken);
    }

    public GoogleUserInfoDTO googleUserInfo(String accessToken) throws JsonProcessingException {
        return googleOauth2Service.googleUserInfo(accessToken);
    }

}
