package com.targetcoders.sowing.member.dao;

import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.member.domain.SeedType;
import com.targetcoders.sowing.member.repository.SeedTypeRepository;
import com.targetcoders.sowing.seed.domain.DefaultSeedType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SeedTypeDao {

    private final SeedTypeRepository seedTypeRepository;

    public List<SeedType> saveSeedTypes(Member member, List<DefaultSeedType> defaultSeedTypes) {
        return seedTypeRepository.save(member, defaultSeedTypes);
    }

    public List<SeedType> findSeedTypesByUsername(String username) {
        return seedTypeRepository.findSeedTypesByUsername(username);
    }
}
