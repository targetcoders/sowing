package com.targetcoders.sowing.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.targetcoders.sowing.common.LoginConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class GoogleOauth2Manager {

    @Value("${oauth2.client-secret}")
    private String clientSecret;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GoogleAuthorizationDTO googleAuthorization(String code) throws JsonProcessingException {
        String url = "https://oauth2.googleapis.com/token";

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", LoginConstants.CLIENT_ID);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", LoginConstants.REDIRECT_URI);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

        return objectMapper.readValue(response.getBody(), GoogleAuthorizationDTO.class);
    }

    public GoogleUserInfoDTO googleUserInfo(String accessToken) throws JsonProcessingException {
        System.out.println("LoginService.googleUserInfo");
        String url = "https://www.googleapis.com/oauth2/v1/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

        return objectMapper.readValue(response.getBody(), GoogleUserInfoDTO.class);
    }
}
