package com.targetcoders.sowing.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.targetcoders.sowing.member.CreateMemberDTO;
import com.targetcoders.sowing.member.Member;
import com.targetcoders.sowing.member.MemberService;
import com.targetcoders.sowing.member.Tokens;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final GoogleOauth2Manager googleOauth2Manager;
    private final MemberService memberService;

    public Tokens tokens(String code) throws JsonProcessingException {
        GoogleAuthorizationDTO googleAuthorizationDTO = googleOauth2Manager.googleAuthorization(code);
        String accessToken = googleAuthorizationDTO.getAccessToken();
        String refreshToken = googleAuthorizationDTO.getRefreshToken();
        Tokens tokens = new Tokens(accessToken, refreshToken);
        System.out.println("accessToken = " + accessToken);
        System.out.println("refreshToken = " + refreshToken);
        return tokens;
    }

    public GoogleUserInfoDTO googleUserInfo(String accessToken) throws JsonProcessingException {
        GoogleUserInfoDTO googleUserInfoDTO = googleOauth2Manager.googleUserInfo(accessToken);
        System.out.println("googleUserInfoDTO = " + googleUserInfoDTO);
        return googleUserInfoDTO;
    }

    public Member joinMember(GoogleUserInfoDTO googleUserInfoDTO, String accessToken, String refreshToken) {
        CreateMemberDTO createMemberDTO = new CreateMemberDTO(googleUserInfoDTO.getEmail(), googleUserInfoDTO.getName(), accessToken, refreshToken);
        return memberService.saveMember(createMemberDTO);
    }

}
