package com.targetcoders.sowing.seed.service;

import com.targetcoders.sowing.authentication.domain.JwtToken;
import com.targetcoders.sowing.member.CreateMemberDTO;
import com.targetcoders.sowing.member.Member;
import com.targetcoders.sowing.member.MemberService;
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
    @Autowired SeedService seedService;
    @Autowired MemberService memberService;

    @Test
    @DisplayName("등록된 시드 리스트를 시드 그룹 리스트로 변환해서 반환")
    public void seedGroupList() {
        //given
        CreateMemberDTO createMemberDTO = new CreateMemberDTO("greenneuron", "nickname", "accessToken","refreshToken", new JwtToken("a.b.c"));
        Member member = memberService.saveMember(createMemberDTO);
        LocalDateTime now = LocalDateTime.now();
        Seed seed1 = Seed.create(SeedType.PLAY, member, "제목", "내용", now);
        Seed seed2 = Seed.create(SeedType.STUDY, member, "제목", "내용", now.minusDays(1));
        Seed seed3 = Seed.create(SeedType.READ, member, "제목", "내용", now.minusDays(2));
        seedService.saveSeed(seed1);
        seedService.saveSeed(seed2);
        seedService.saveSeed(seed3);

        //when
        List<SeedGroup> seedGroups = seedGroupService.seedGroupsByUsername(member.getUsername());

        //then
        Assertions.assertThat(seedGroups.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("등록된 시드가 없으면 시드 그룹 리스트를 Empty 리스트로 반환")
    public void seedGroupListReturnEmptyList() {
        //given
        CreateMemberDTO createMemberDTO = new CreateMemberDTO("greenneuron", "nickname", "accessToken","refreshToken", new JwtToken("a.b.c"));
        Member member = memberService.saveMember(createMemberDTO);
        //when
        List<SeedGroup> seedGroups = seedGroupService.seedGroupsByUsername(member.getUsername());

        //then
        Assertions.assertThat(seedGroups.size()).isEqualTo(0);
    }

}