package com.targetcoders.sowing.seed.service;

import com.targetcoders.sowing.member.dao.SeedTypeDao;
import com.targetcoders.sowing.member.domain.SeedType;
import com.targetcoders.sowing.seed.dao.SeedDao;
import com.targetcoders.sowing.seed.domain.Seed;
import com.targetcoders.sowing.seed.domain.TypeCounter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeedOverviewService {

    private final SeedDao seedDao;
    private final SeedTypeDao seedTypeDao;

    @Transactional
    public TypeCounter countSeeds(String username) {
        List<Seed> seeds = seedDao.findSeedsByUsername(username);
        List<SeedType> seedTypes = seedTypeDao.findSeedTypesByUsername(username);
        TypeCounter typeCounter = new TypeCounter(seedTypes);
        typeCounter.count(seeds);
        return typeCounter;

    }
}
