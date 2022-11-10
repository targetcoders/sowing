package com.targetcoders.sowing.member.dto;

import com.targetcoders.sowing.authentication.domain.JwtToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CreateMemberDTO {

    private String email;
    private String nickname;
    private String googleAccessToken;
    private String googleRefreshToken;
    private JwtToken sowingRefreshToken;

}
