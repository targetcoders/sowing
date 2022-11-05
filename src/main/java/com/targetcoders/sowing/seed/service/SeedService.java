package com.targetcoders.sowing.seed.service;

import com.targetcoders.sowing.member.Member;
import com.targetcoders.sowing.member.MemberRepository;
import com.targetcoders.sowing.seed.dto.UpdateSeedDTO;
import com.targetcoders.sowing.seed.dao.SeedDao;
import com.targetcoders.sowing.seed.domain.Seed;
import com.targetcoders.sowing.seed.domain.SeedForm;
import com.targetcoders.sowing.seed.domain.SeedGroup;
import com.targetcoders.sowing.seed.domain.SeedType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeedService {

    private final SeedDao seedDao;
    private final MemberRepository memberRepository;
    private final SeedGroupService seedGroupService;

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
    public Long saveSeed(SeedForm seedForm) {
        SeedType selectType = seedForm.getSelectType();
        String content = seedForm.getContent();
        String username = seedForm.getUsername();
        String title = seedForm.getTitle();
        String sowingDateTime = defaultSowingDateTime(seedForm.getSowingDate());
        Member member = memberRepository.findByUsername(username).get(0);
        Seed seed = Seed.create(selectType, member, title, content, LocalDateTime.parse(sowingDateTime));
        seedDao.saveSeed(seed);
        return seed.getId();
    }

    @Transactional
    public List<SeedGroup> findSeedGroups(String username) {
        List<Member> members = memberRepository.findByUsername(username);
        if (members.isEmpty()) {
            return new ArrayList<>();
        }
        return seedGroupService.seedGroupsByUsername(members.get(0).getUsername());
    }

    private String defaultSowingDateTime(String sowingDate) {
        return sowingDate + "T00:00:00";
    }

    public List<Seed> findSeedsByUsername(String username) {
        return seedDao.findSeedsByUsername(username);
    }
}
