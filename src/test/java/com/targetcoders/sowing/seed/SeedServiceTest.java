package com.targetcoders.sowing.seed;

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

    @Test
    @DisplayName("ID로 조회")
    @Transactional
    void saveAndFindOne() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Seed seed = Seed.create(SeedType.PLAY,100L, "제목", "내용", now);

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
    @DisplayName("찾지 못하면 null 반환")
    @Transactional
    void seedNotFound() {
        //then
        assertThat(seedService.findSeedById(0L)).isNull();
    }

    @Test
    @DisplayName("업데이트")
    @Transactional
    void updateSeed() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Seed seed = Seed.create(SeedType.PLAY,100L, "제목", "내용", now);
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
    @DisplayName("ID로 삭제")
    @Transactional
    void removeSeed() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Seed seed = Seed.create(SeedType.PLAY,100L, "제목", "내용", now);
        Long saveId = seedService.saveSeed(seed);
        assertThat(seedService.findSeedById(saveId)).isNotNull();

        //when
        seedService.removeSeedById(saveId);

        //then
        assertThat(seedService.findSeedById(saveId)).isNull();
    }
}