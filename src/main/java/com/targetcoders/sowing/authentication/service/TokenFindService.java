package com.targetcoders.sowing.authentication.service;

import com.targetcoders.sowing.authentication.dao.MemberTokenDao;
import com.targetcoders.sowing.authentication.domain.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class TokenFindService {

    private final MemberTokenDao memberTokenDao;

    @Transactional
    public JwtToken sowingRefreshToken(String email) {
        String sowingRefreshToken = memberTokenDao.findSowingRefreshToken(email);
        return new JwtToken(sowingRefreshToken);
    }
}
