package com.targetcoders.sowing.seed.service;

import com.targetcoders.sowing.member.dao.MemberDao;
import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.member.domain.SeedType;
import com.targetcoders.sowing.seed.dao.SeedDao;
import com.targetcoders.sowing.seed.domain.Seed;
import com.targetcoders.sowing.seed.dto.SeedCreateDTO;
import com.targetcoders.sowing.seed.dto.SeedUpdateDTO;
import com.targetcoders.sowing.settings.dao.SeedTypeDao;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeedService {

    private final SeedDao seedDao;
    private final SeedTypeDao seedTypeDao;
    private final MemberDao memberDao;

    @Transactional
    public Long saveSeed(Seed seed) {
        seedDao.saveSeed(seed);
        return seed.getId();
    }

    @Transactional
    public Seed findSeedById(Long seedId) {
        return seedDao.findSeedById(seedId);
    }

    @Transactional
    public void removeSeedById(Long seedId) {
        seedDao.removeSeedById(seedId);
    }

    @Transactional
    public void updateSeed(SeedUpdateDTO seedUpdateDto) {
        seedDao.updateSeed(seedUpdateDto);
    }

    @Transactional
    public Long saveSeed(SeedCreateDTO seedCreateDTO) throws NotFoundException {
        String content = seedCreateDTO.getContent();
        String username = seedCreateDTO.getUsername();
        String title = seedCreateDTO.getTitle();
        Member member = memberDao.findByUsername(username);
        SeedType seedType = seedTypeDao.findSeedTypeById(seedCreateDTO.getSeedTypeId());
        Seed seed = Seed.create(seedType, member, title, content, seedCreateDTO.getSowingDate());
        seedDao.saveSeed(seed);
        return seed.getId();
    }

    @Transactional
    public boolean isUsedSeedType(String username, String seedTypeName) {
        return seedDao.findSeeds(username)
                .stream()
                .anyMatch(seed -> seed.getSeedType().getName().equals(seedTypeName));
    }

}
