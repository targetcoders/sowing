package com.targetcoders.sowing.seed.service;

import com.targetcoders.sowing.member.dao.MemberDao;
import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.seed.dao.SeedDao;
import com.targetcoders.sowing.seed.domain.Seed;
import com.targetcoders.sowing.seed.domain.TypeCounter;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeedOverviewService {

    private final SeedDao seedDao;
    private final MemberDao memberDao;

    @Transactional
    public TypeCounter countSeeds(String username) throws NotFoundException {
        List<Seed> seeds = seedDao.findSeedsByUsername(username);
        Member member = memberDao.findByUsername(username);
        TypeCounter typeCounter = new TypeCounter(member.getSettings().getSeedTypes());
        typeCounter.count(seeds);
        return typeCounter;

    }
}
