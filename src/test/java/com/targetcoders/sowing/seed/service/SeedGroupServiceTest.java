package com.targetcoders.sowing.seed.service;

import com.targetcoders.sowing.member.GoogleTokens;
import com.targetcoders.sowing.member.Member;
import com.targetcoders.sowing.seed.domain.Seed;
import com.targetcoders.sowing.seed.domain.SeedGroup;
import com.targetcoders.sowing.seed.domain.SeedType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class SeedGroupServiceTest {

    @Autowired SeedGroupService seedGroupService;

    @Test
    @DisplayName("등록된 시드 리스트를 시드 그룹 리스트로 변환해서 반환")
    public void seedGroupList() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Member member = Member.create("greenneuron", "nickname", new GoogleTokens("accessToken","refreshToken"), "sowingRefreshToken", now, now);
        Seed.create(SeedType.PLAY, member, "제목", "내용", now);
        Seed.create(SeedType.STUDY, member, "제목", "내용", now.minusDays(1));
        Seed.create(SeedType.READ, member, "제목", "내용", now.minusDays(2));

        //when
        List<SeedGroup> seedGroups = seedGroupService.seedGroupList(member);

        //then
        Assertions.assertThat(seedGroups.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("등록된 시드가 없으면 시드 그룹 리스트를 Empty 리스트로 반환")
    public void seedGroupListReturnEmptyList() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Member member = Member.create("greenneuron", "nickname", new GoogleTokens("accessToken","refreshToken"), "sowingRefreshToken", now, now);

        //when
        List<SeedGroup> seedGroups = seedGroupService.seedGroupList(member);

        //then
        Assertions.assertThat(seedGroups.size()).isEqualTo(0);
    }

}