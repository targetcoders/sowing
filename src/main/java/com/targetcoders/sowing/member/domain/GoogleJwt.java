package com.targetcoders.sowing.member.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "google_jwt")
public class GoogleJwt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "google_jwt_id")
    private Long id;
    @Column(name = "access_token", nullable = false)
    private String accessToken;
    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    public GoogleJwt(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public GoogleJwt() {

    }
}
