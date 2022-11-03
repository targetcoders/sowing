package com.targetcoders.sowing.seed.service;

import com.targetcoders.sowing.member.Member;
import com.targetcoders.sowing.seed.domain.Seed;
import com.targetcoders.sowing.seed.domain.SeedGroup;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class SeedGroupService {

    public List<SeedGroup> seedGroupList(Member member) {
        List<SeedGroup> result = new ArrayList<>();
        Deque<Seed> seedDeque = new ArrayDeque<>(member.getSeedList());

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
            Collections.sort(seedGroup.getSeedList());
            result.add(seedGroup);
        }

        Collections.sort(result);
        return result;
    }

    
}
