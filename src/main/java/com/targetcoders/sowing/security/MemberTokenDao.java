package com.targetcoders.sowing.security;

import com.targetcoders.sowing.member.Member;
import com.targetcoders.sowing.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberTokenDao {

    private final MemberRepository  memberRepository;

    public String findSowingRefreshToken(String email) {
        Member member = memberRepository.findByUsername(email).get(0);
        return member.getSowingRefreshToken();
    }
    public void updateSowingRefreshToken(String email, String sowingRefreshToken) {
        Member member = memberRepository.findByUsername(email).get(0);
        member.setSowingRefreshToken(sowingRefreshToken);
    }

    public void updateAllTokens(String email, String googleAccessToken, String googleRefreshToken, String sowingRefreshToken) {
        Member member = memberRepository.findByUsername(email).get(0);
        if (googleRefreshToken != null && !googleRefreshToken.equals("")) {
            member.getGoogleTokens().setRefreshToken(googleRefreshToken);
        }
        member.getGoogleTokens().setAccessToken(googleAccessToken);
        member.setSowingRefreshToken(sowingRefreshToken);
    }
}
