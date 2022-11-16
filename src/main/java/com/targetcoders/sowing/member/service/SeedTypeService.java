package com.targetcoders.sowing.member.service;

import com.targetcoders.sowing.member.dao.SeedTypeDao;
import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.member.domain.SeedType;
import com.targetcoders.sowing.seed.domain.DefaultSeedType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeedTypeService {

    private final SeedTypeDao seedTypeDao;

    @Transactional
    public List<SeedType> saveSeedTypes(Member member) {
        List<DefaultSeedType> defaultSeedTypes = Arrays.asList(DefaultSeedType.values());
        return seedTypeDao.saveSeedTypes(member, defaultSeedTypes);
    }

}
