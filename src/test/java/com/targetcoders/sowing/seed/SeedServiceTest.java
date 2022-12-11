package com.targetcoders.sowing.seed;

import com.targetcoders.sowing.member.domain.GoogleTokens;
import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.seedtype.domain.SeedType;
import com.targetcoders.sowing.settings.domain.Settings;
import com.targetcoders.sowing.member.service.MemberService;
import com.targetcoders.sowing.seed.domain.Seed;
import com.targetcoders.sowing.seedtype.domain.DefaultSeedType;
import com.targetcoders.sowing.seed.dto.SeedUpdateDTO;
import com.targetcoders.sowing.seed.service.SeedService;
import com.targetcoders.sowing.seedtype.repository.SeedTypeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SeedServiceTest {

    public static final LocalDate LOCAL_DATE = LocalDate.now();
    @Autowired
    SeedService seedService;
    @Autowired
    MemberService memberService;
    @Autowired
    SeedTypeRepository seedTypeRepository;

    @Test
    @DisplayName("시드 조회")
    @Transactional
    void saveAndFindOne() {
        //given
        Member member = createDefaultMember();
        Seed seed = Seed.create(new SeedType(DefaultSeedType.PLAY.toString()), member, "제목", "내용", LOCAL_DATE);

        //when
        Long saveId = seedService.saveSeed(seed);

        //then
        Seed getSeed = seedService.findSeedById(saveId);
        assertThat(getSeed.getId()).isEqualTo(getSeed.getId());
        assertThat(getSeed.getSeedType().getName()).isEqualTo("PLAY");
        assertThat(getSeed.getTitle()).isEqualTo("제목");
        assertThat(getSeed.getContent()).isEqualTo("내용");
        assertThat(getSeed.getSowingDate()).isEqualTo(LOCAL_DATE);
    }

    @Test
    @DisplayName("시드 수정")
    @Transactional
    void updateSeed() {
        //given
        Member member = createDefaultMember();
        SeedType seedTypePlay = new SeedType(DefaultSeedType.PLAY.toString());
        seedTypeRepository.saveSeedType(seedTypePlay);
        Seed seed = Seed.create(seedTypePlay, member, "제목", "내용", LOCAL_DATE);
        Long saveId = seedService.saveSeed(seed);

        //when
        Seed findSeed = seedService.findSeedById(saveId);
        SeedUpdateDTO seedUpdateDto = new SeedUpdateDTO(findSeed.getId(), seedTypePlay, "변경된 제목", "변경된 내용", LOCAL_DATE);
        seedService.updateSeed(seedUpdateDto);

        //then
        Seed updateSeed = seedService.findSeedById(findSeed.getId());
        assertThat(updateSeed.getSeedType()).isEqualTo(seedTypePlay);
        assertThat(updateSeed.getTitle()).isEqualTo("변경된 제목");
        assertThat(updateSeed.getContent()).isEqualTo("변경된 내용");
        assertThat(updateSeed.getSowingDate()).isEqualTo(LOCAL_DATE);
    }

    @Test
    @DisplayName("시드 삭제, 찾지 못하면 null")
    @Transactional
    void removeSeed() {
        //given
        Member member = createDefaultMember();
        Seed seed = Seed.create(new SeedType(DefaultSeedType.PLAY.toString()),member, "제목", "내용", LOCAL_DATE);
        Long saveId = seedService.saveSeed(seed);
        assertThat(seedService.findSeedById(saveId)).isNotNull();

        //when
        seedService.removeSeedById(saveId);

        //then
        Seed findSeed = seedService.findSeedById(saveId);
        assertThat(findSeed).isNull();
    }

    private Member createDefaultMember() {
        return Member.create("greenneuron", "nickname", new GoogleTokens("accessToken", "refreshToken"), "sowingRefreshToken", LOCAL_DATE, LOCAL_DATE, Settings.create());
    }

    @Test
    @DisplayName("시드 리스트를 Type Value 기준으로 오름차순 정렬")
    public void sortedSeedList() {
        //given
        List<Seed> seedList = new ArrayList<>();
        Member member = createDefaultMember();
        Seed seed1 = Seed.create(new SeedType(DefaultSeedType.PLAY.toString()), member, "제목", "내용", LOCAL_DATE);
        Seed seed2 = Seed.create(new SeedType(DefaultSeedType.READ.toString()), member, "제목", "내용", LOCAL_DATE);
        Seed seed3 = Seed.create(new SeedType(DefaultSeedType.STUDY.toString()), member, "제목", "내용", LOCAL_DATE);
        Seed seed4 = Seed.create(new SeedType(DefaultSeedType.DATE.toString()), member, "제목", "내용", LOCAL_DATE);
        seedList.add(seed1);
        seedList.add(seed2);
        seedList.add(seed3);
        seedList.add(seed4);

        //when
        Collections.sort(seedList);

        //then
        assertThat(seedList.get(0)).isEqualTo(seed4);
        assertThat(seedList.get(1)).isEqualTo(seed1);
        assertThat(seedList.get(2)).isEqualTo(seed2);
        assertThat(seedList.get(3)).isEqualTo(seed3);
    }
}