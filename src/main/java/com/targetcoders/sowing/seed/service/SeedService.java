package com.targetcoders.sowing.seed.service;

import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.member.dao.MemberDao;
import com.targetcoders.sowing.seed.dao.SeedDao;
import com.targetcoders.sowing.seed.domain.Seed;
import com.targetcoders.sowing.seed.domain.SeedForm;
import com.targetcoders.sowing.seed.domain.SeedType;
import com.targetcoders.sowing.seed.dto.UpdateSeedDTO;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
    public Long saveSeed(SeedForm seedForm) throws NotFoundException {
        SeedType selectType = seedForm.getSelectType();
        String content = seedForm.getContent();
        String username = seedForm.getUsername();
        String title = seedForm.getTitle();
        String sowingDateTime = defaultSowingDateTime(seedForm.getSowingDate());
        Member member = memberDao.findByUsername(username);
        Seed seed = Seed.create(selectType, member, title, content, LocalDateTime.parse(sowingDateTime));
        seedDao.saveSeed(seed);
        return seed.getId();
    }

    private String defaultSowingDateTime(String sowingDate) {
        return sowingDate + "T00:00:00";
    }

    public List<Seed> findSeedsByUsername(String username) {
        return seedDao.findSeedsByUsername(username);
    }
}
