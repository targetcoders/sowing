package com.targetcoders.sowing.authentication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.targetcoders.sowing.authentication.dto.GoogleUserInfoDTO;
import com.targetcoders.sowing.member.GoogleTokens;

public interface GoogleLoginService {
    GoogleTokens googleTokens(String code) throws JsonProcessingException;
    GoogleUserInfoDTO googleUserInfo(String accessToken) throws JsonProcessingException;
}
