package com.targetcoders.sowing.authentication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.targetcoders.sowing.authentication.dto.GoogleUserInfoDTO;
import com.targetcoders.sowing.member.domain.GoogleJwt;

public interface GoogleLoginService {
    GoogleJwt googleLogin(String code) throws JsonProcessingException;
    GoogleUserInfoDTO googleUserInfo(String accessToken) throws JsonProcessingException;
}
