package com.targetcoders.sowing.seed.service;

import com.targetcoders.sowing.seed.dao.SeedDao;
import com.targetcoders.sowing.seed.domain.Seed;
import com.targetcoders.sowing.seed.domain.SeedGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SeedGroupService {

    private final SeedDao seedDao;

    @Transactional
    public List<SeedGroup> seedGroupsByUsername(String username) {
        List<SeedGroup> result = new ArrayList<>();
        Deque<Seed> seedDeque = new ArrayDeque<>(seedDao.findSeedsByUsername(username));

        while (!seedDeque.isEmpty()) {
            LocalDate groupDate = seedDeque.peekFirst().getSowingDate().toLocalDate();
            SeedGroup seedGroup = SeedGroup.create(groupDate);
            while (!seedDeque.isEmpty()) {
                Seed seed = seedDeque.peekFirst();
                LocalDate seedDate = seed.getSowingDate().toLocalDate();
                if (!groupDate.equals(seedDate)) {
                    break;
                }
                seedGroup.addSeed(seed);
                seedDeque.pollFirst();
            }
            List<Seed> seeds = seedGroup.getSeedList();
            Collections.sort(seeds);
            result.add(seedGroup);
        }

        Collections.sort(result);
        return result;
    }

    
}
