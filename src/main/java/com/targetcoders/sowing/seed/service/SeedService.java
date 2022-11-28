package com.targetcoders.sowing.seed.service;

import com.targetcoders.sowing.member.dao.MemberDao;
import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.seed.dao.SeedDao;
import com.targetcoders.sowing.seed.domain.Seed;
import com.targetcoders.sowing.seed.dto.SeedFormDTO;
import com.targetcoders.sowing.seed.dto.UpdateSeedDTO;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeedService {

    private final SeedDao seedDao;
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
    public void updateSeed(UpdateSeedDTO updateSeedDto) {
        seedDao.updateSeed(updateSeedDto);
    }

    @Transactional
    public Long saveSeed(SeedFormDTO seedFormDTO) throws NotFoundException {
        String selectType = seedFormDTO.getSelectType();
        String content = seedFormDTO.getContent();
        String username = seedFormDTO.getUsername();
        String title = seedFormDTO.getTitle();
        Member member = memberDao.findByUsername(username);
        Seed seed = Seed.create(selectType, member, title, content, seedFormDTO.getSowingDate());
        seedDao.saveSeed(seed);
        return seed.getId();
    }

    @Transactional
    public boolean isUsedSeedType(String username, String seedTypeName) {
        return seedDao.findSeeds(username)
                .stream()
                .anyMatch(seed -> seed.getType().equals(seedTypeName));
    }
}
