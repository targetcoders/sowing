package com.targetcoders.sowing.member.repository;

import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.member.domain.SeedType;
import com.targetcoders.sowing.seed.domain.DefaultSeedType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SeedTypeRepository {

    private final EntityManager em;

    public List<SeedType> save(Member member, List<DefaultSeedType> defaultSeedTypes) {
        List<SeedType> result = new ArrayList<>();
        for (DefaultSeedType defaultSeedType : defaultSeedTypes) {
            String name = defaultSeedType.toString();
            SeedType seedType = SeedType.create(member, name);
            em.persist(seedType);
            result.add(seedType);
        }
        return result;
    }
}
