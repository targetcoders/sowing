package com.targetcoders.sowing.authentication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.targetcoders.sowing.authentication.dto.GoogleAuthorizationDTO;
import com.targetcoders.sowing.authentication.dto.GoogleUserInfoDTO;

public interface GoogleOauth2Service {
    GoogleAuthorizationDTO googleAuthorization(String code) throws JsonProcessingException;
    GoogleUserInfoDTO googleUserInfo(String accessToken) throws JsonProcessingException;
}
