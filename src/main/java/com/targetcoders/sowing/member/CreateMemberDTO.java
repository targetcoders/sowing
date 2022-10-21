package com.targetcoders.sowing.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CreateMemberDTO {

    private String email;
    private String nickname;
    private String password;

}
