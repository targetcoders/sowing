package com.targetcoders.sowing.seed;

import com.targetcoders.sowing.member.Member;
import com.targetcoders.sowing.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SeedServiceTest {

    @Autowired
    SeedService seedService;
    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("시드 조회")
    @Transactional
    void saveAndFindOne() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Member member = Member.create("greenneuron", "nickname", now, now);
        Seed seed = Seed.create(SeedType.PLAY, member, "제목", "내용", now);

        //when
        Long saveId = seedService.saveSeed(seed);

        //then
        Seed getSeed = seedService.findSeedById(saveId);
        assertThat(getSeed.getId()).isEqualTo(getSeed.getId());
        assertThat(getSeed.getType().toString()).isEqualTo("PLAY");
        assertThat(getSeed.getTitle()).isEqualTo("제목");
        assertThat(getSeed.getContent()).isEqualTo("내용");
        assertThat(getSeed.getSowingDate()).isEqualTo(now);
    }

    @Test
    @DisplayName("시드 수정")
    @Transactional
    void updateSeed() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Member member = Member.create("greenneuron", "nickname", now, now);
        Seed seed = Seed.create(SeedType.PLAY, member, "제목", "내용", now);
        Long saveId = seedService.saveSeed(seed);

        //when
        Seed findSeed = seedService.findSeedById(saveId);
        LocalDateTime updateDate = LocalDateTime.now();
        UpdateSeedDTO updateSeedDto = new UpdateSeedDTO(findSeed.getId(), SeedType.STUDY, "변경된 제목", "변경된 내용", updateDate);
        seedService.updateSeed(updateSeedDto);

        //then
        Seed updateSeed = seedService.findSeedById(findSeed.getId());
        assertThat(updateSeed.getType()).isEqualTo(SeedType.STUDY);
        assertThat(updateSeed.getTitle()).isEqualTo("변경된 제목");
        assertThat(updateSeed.getContent()).isEqualTo("변경된 내용");
        assertThat(updateSeed.getSowingDate()).isEqualTo(updateDate);
    }

    @Test
    @DisplayName("시드 삭제, 찾지 못하면 null")
    @Transactional
    void removeSeed() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Member member = Member.create("greenneuron", "nickname", now, now);
        Seed seed = Seed.create(SeedType.PLAY,member, "제목", "내용", now);
        Long saveId = seedService.saveSeed(seed);
        assertThat(seedService.findSeedById(saveId)).isNotNull();

        //when
        seedService.removeSeedById(saveId);

        //then
        Seed findSeed = seedService.findSeedById(saveId);
        assertThat(findSeed).isNull();
    }
}