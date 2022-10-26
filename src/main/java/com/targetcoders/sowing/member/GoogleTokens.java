package com.targetcoders.sowing.member;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter
public class GoogleTokens {

    private String accessToken;
    private String refreshToken;

    public GoogleTokens(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public GoogleTokens() {

    }
}
