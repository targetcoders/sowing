package com.targetcoders.sowing.member.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
public class GoogleTokens {

    @Id
    @GeneratedValue
    @Column(name = "google_tokens_id")
    private Long id;
    private String accessToken;
    private String refreshToken;

    public GoogleTokens(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public GoogleTokens() {

    }
}
