package com.targetcoders.sowing.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TokenUpdateService {

    private final MemberTokenDao memberTokenDao;

    @Transactional
    public void updateSowingRefreshToken(String email, String sowingRefreshToken) {
        memberTokenDao.updateSowingRefreshToken(email, sowingRefreshToken);
    }

    @Transactional
    public void updateAllTokens(String email,String googleAccessToken, String googleRefreshToken, String swingRefreshToken) {
        memberTokenDao.updateAllTokens(email, googleAccessToken, googleRefreshToken, swingRefreshToken);
    }

}
