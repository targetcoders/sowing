package com.targetcoders.sowing.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class TokenFindService {

    private final MemberTokenDao memberTokenDao;

    @Transactional
    public String sowingRefreshToken(String email) {
        return memberTokenDao.findSowingRefreshToken(email);
    }
}
