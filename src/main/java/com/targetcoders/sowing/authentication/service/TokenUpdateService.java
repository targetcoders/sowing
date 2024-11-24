package com.targetcoders.sowing.authentication.service;

import com.targetcoders.sowing.authentication.dao.MemberTokenDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TokenUpdateService {

    private final MemberTokenDao memberTokenDao;

    @Transactional
    public void updateAllTokens(String email,String googleAccessToken, String googleRefreshToken) {
        memberTokenDao.updateAllTokens(email, googleAccessToken, googleRefreshToken);
    }

}
