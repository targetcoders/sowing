package com.targetcoders.sowing.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GoogleAuthorizationDTO {

    @JsonProperty(value = "access_token")
    private String accessToken;
    @JsonProperty(value = "refresh_token")
    private String refreshToken;
    @JsonProperty(value = "expires_in")
    private String expiresIn;
    @JsonProperty(value = "scope")
    private String scope;
    @JsonProperty(value = "token_type")
    private String tokenType;
    @JsonProperty(value = "id_token")
    private String idToken;

}
