package com.targetcoders.sowing.seed;

import com.targetcoders.sowing.member.Member;
import com.targetcoders.sowing.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        String sowingDateTime = defaultSowingDateTime(seedForm.getSowingDate());
        Member member = memberRepository.findByUsername(username).get(0);
        Seed.create(selectType, member, title, content, LocalDateTime.parse(sowingDateTime));
        return member.getId();
    }

    @Transactional
    public List<SeedGroup> findSeedGroups(String username) {
        List<Member> members = memberRepository.findByUsername(username);
        if (members.isEmpty()) {
            return new ArrayList<>();
        }
        return members.get(0).seedGroupList();
    }

    private String defaultSowingDateTime(String sowingDate) {
        return sowingDate + "T00:00:00";
    }

}
