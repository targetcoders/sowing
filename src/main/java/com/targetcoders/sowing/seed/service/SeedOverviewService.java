package com.targetcoders.sowing.seed.service;

import com.targetcoders.sowing.seed.dao.SeedDao;
import com.targetcoders.sowing.seed.domain.Seed;
import com.targetcoders.sowing.seed.domain.TypeCounter;
import com.targetcoders.sowing.seed.domain.SeedType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeedOverviewService {

    private final SeedDao seedDao;

    @Transactional
    public TypeCounter countSeeds(String username) {
        List<Seed> seeds = seedDao.findSeedsByUsername(username);
        TypeCounter typeCounter = new TypeCounter();
        for (Seed seed : seeds) {
            if (seed.getType() == SeedType.DATE) {
                typeCounter.incDate();
            } else if( seed.getType() == SeedType.PLAY) {
                typeCounter.incPlay();
            } else if( seed.getType() == SeedType.READ) {
                typeCounter.incRead();
            } else {
                typeCounter.incStudy();
            }
        }
        return typeCounter;

    }
}
