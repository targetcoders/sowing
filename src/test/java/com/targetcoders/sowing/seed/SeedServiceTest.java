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
        seedService.saveSeed(seed);

        //then
        Seed getSeed = seedService.findById(1L);
        assertThat(getSeed.getId()).isEqualTo(1L);
        assertThat(getSeed.getType().toString()).isEqualTo("PLAY");
        assertThat(getSeed.getTitle()).isEqualTo("제목");
        assertThat(getSeed.getContent()).isEqualTo("내용");
        assertThat(getSeed.getSowingDate()).isEqualTo(now);
    }

    @Test
    @DisplayName("찾지 못하면 null 반환")
    @Transactional
    void seedNotFound() {
        //when
        Seed result = seedService.findById(0L);

        //then
        assertThat(result).isEqualTo(null);
    }
}