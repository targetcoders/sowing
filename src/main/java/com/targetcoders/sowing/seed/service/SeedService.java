package com.targetcoders.sowing.seed.service;

import com.targetcoders.sowing.member.dao.MemberDao;
import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.seedtype.domain.SeedType;
import com.targetcoders.sowing.seed.dao.SeedDao;
import com.targetcoders.sowing.seed.domain.Seed;
import com.targetcoders.sowing.seed.dto.SeedCreateDTO;
import com.targetcoders.sowing.seed.dto.SeedUpdateDTO;
import com.targetcoders.sowing.seedtype.dao.SeedTypeDao;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        Optional<SeedType> seedType = seedTypeDao.findSeedTypeById(username, seedCreateDTO.getSeedTypeId());
        if (seedType.isEmpty()) {
            throw new NotFoundException("SeedType을 찾을 수 없습니다. username="+username);
        }
        Seed seed = Seed.create(seedType.get(), member, title, content, seedCreateDTO.getSowingDate());
        seedDao.saveSeed(seed);
        return seed.getId();
    }

}
