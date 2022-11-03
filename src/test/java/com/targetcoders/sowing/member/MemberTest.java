package com.targetcoders.sowing.member;

import com.targetcoders.sowing.authentication.service.JwtTokenService;
import com.targetcoders.sowing.seed.domain.Seed;
import com.targetcoders.sowing.seed.domain.SeedGroup;
import com.targetcoders.sowing.seed.service.SeedService;
import com.targetcoders.sowing.seed.domain.SeedType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberTest {

    @Autowired SeedService seedService;
    @Autowired MemberService memberService;
    @Autowired EntityManager em;
    @Autowired JwtTokenService jwtTokenService;

    @Test
    @DisplayName("멤버를 조회하면 해당 멤버의 모든 시드도 조회된다")
    @Transactional
    void saveAndFind() {
        //given
        LocalDateTime now = LocalDateTime.now();
        CreateMemberDTO createMemberDTO = new CreateMemberDTO("greenneuron@naver.com", "nickname", "accessToken", "refreshToken", jwtTokenService.createDefaultToken());
        Member saveMember = memberService.saveMember(createMemberDTO);

        //when
        Seed seed1 = Seed.create(SeedType.STUDY, saveMember, "제목1", "내용1", now);
        seedService.saveSeed(seed1);
        Seed seed2 = Seed.create(SeedType.STUDY, saveMember, "제목2", "내용2", now);
        seedService.saveSeed(seed2);
        Seed seed3 = Seed.create(SeedType.STUDY, saveMember, "제목3", "내용3", now);
        seedService.saveSeed(seed3);

        //when
        Member findMember2 = memberService.findMemberById(saveMember.getId());

        em.flush();

        //then
        assertThat(findMember2.getSeedList().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("모든 멤버 조회")
    @Transactional
    void findAll() {
        //given
        CreateMemberDTO createMemberDTO1 = new CreateMemberDTO("greenneuron", "nickname", "accessToken", "refreshToken", jwtTokenService.createDefaultToken());
        memberService.saveMember(createMemberDTO1);
        CreateMemberDTO createMemberDTO2 = new CreateMemberDTO("greenneuron2", "nickname2", "accessToken2", "refreshToken", jwtTokenService.createDefaultToken());
        memberService.saveMember(createMemberDTO2);

        //when
        List<Member> allMembers = memberService.findAllMembers();

        //then
        assertThat(allMembers.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("멤버 삭제, 찾지 못하면 null")
    @Transactional
    public void remove() {
        //given
        CreateMemberDTO createMemberDTO1 = new CreateMemberDTO("greenneuron", "nickname", "accessToken", "refreshToken", jwtTokenService.createDefaultToken());
        Member saveMember = memberService.saveMember(createMemberDTO1);

        //when
        memberService.removeMember(saveMember);

        //then
        Member findMember = memberService.findMemberById(saveMember.getId());
        assertThat(findMember).isNull();
    }

    @Test
    @DisplayName("멤버 수정")
    @Transactional
    public void update() {
        //given
        CreateMemberDTO createMemberDTO1 = new CreateMemberDTO("greenneuron", "nickname",  "accessToken", "refreshToken", jwtTokenService.createDefaultToken());
        Member saveMember = memberService.saveMember(createMemberDTO1);
        UpdateMemberDTO updateMemberDTO = new UpdateMemberDTO();
        updateMemberDTO.setId(saveMember.getId());
        updateMemberDTO.setUsername("변경된 이름");
        updateMemberDTO.setNickname("변경된 닉네임");

        //when
        memberService.updateMember(updateMemberDTO);

        //then
        Member findMember = memberService.findMemberById(saveMember.getId());
        assertThat(findMember.getUsername()).isEqualTo("변경된 이름");
        assertThat(findMember.getNickname()).isEqualTo("변경된 닉네임");
    }


    @Test
    @DisplayName("시드 그룹 리스트를 날짜 기준 내림차순 정렬")
    public void sortedSeedGroupList() {
        //given
        List<SeedGroup> seedGroups = new ArrayList<>();
        SeedGroup seedGroup1 = new SeedGroup(LocalDateTime.now().toLocalDate(), new ArrayList<>());
        SeedGroup seedGroup2 = new SeedGroup(LocalDateTime.now().minusDays(1).toLocalDate(), new ArrayList<>());
        SeedGroup seedGroup3 = new SeedGroup(LocalDateTime.now().minusDays(2).toLocalDate(), new ArrayList<>());
        seedGroups.add(seedGroup3);
        seedGroups.add(seedGroup2);
        seedGroups.add(seedGroup1);

        //when
        Collections.sort(seedGroups);

        //then
        assertThat(seedGroups.get(0)).isEqualTo(seedGroup1);
        assertThat(seedGroups.get(1)).isEqualTo(seedGroup2);
        assertThat(seedGroups.get(2)).isEqualTo(seedGroup3);
    }

}