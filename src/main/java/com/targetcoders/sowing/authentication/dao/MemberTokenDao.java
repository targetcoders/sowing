package com.targetcoders.sowing.authentication.dao;

import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberTokenDao {

    private final MemberRepository  memberRepository;

    public void updateAllTokens(String email, String googleAccessToken, String googleRefreshToken) {
        Member member = memberRepository.findByUsername(email).get(0);
        // refreshToken 은 처음 구글 로그인 할 때만 응답 받으므로 값이 없을 수 있다.
        if (googleRefreshToken != null && !googleRefreshToken.isEmpty()) {
            member.getGoogleJwt().setRefreshToken(googleRefreshToken);
        }
        member.getGoogleJwt().setAccessToken(googleAccessToken);
    }
}
