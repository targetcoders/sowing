package com.targetcoders.sowing.seed;

import com.targetcoders.sowing.member.Member;
import com.targetcoders.sowing.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SeedService {

    private final SeedRepository seedRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long saveSeed(Seed seed) {
        seedRepository.save(seed);
        return seed.getId();
    }

    @Transactional
    public Seed findSeedById(Long seedId) {
        return seedRepository.findById(seedId);
    }

    @Transactional
    public void removeSeedById(Long seedId) {
        seedRepository.removeById(seedId);
    }

    @Transactional
    public void updateSeed(UpdateSeedDTO updateSeedDto) {
        seedRepository.updateSeed(updateSeedDto);
    }

    @Transactional
    public Long saveSeed(SeedForm seedForm) {
        SeedType selectType = seedForm.getSelectType();
        String content = seedForm.getContent();
        String username = seedForm.getUsername();
        String title = seedForm.getTitle();
        Member member = memberRepository.findByUsername(username).get(0);
        Seed.create(selectType, member, title, content, LocalDateTime.now());
        return member.getId();
    }

}
