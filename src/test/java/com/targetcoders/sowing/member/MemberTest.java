package com.targetcoders.sowing.member;

import com.targetcoders.sowing.seed.Seed;
import com.targetcoders.sowing.seed.SeedGroup;
import com.targetcoders.sowing.seed.SeedService;
import com.targetcoders.sowing.seed.SeedType;
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

    @Test
    @DisplayName("멤버를 조회하면 해당 멤버의 모든 시드도 조회된다")
    @Transactional
    void saveAndFind() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Member member = Member.create("greenneuron", "nickname", "password", now, now);
        Long saveMemberId = memberService.saveMember(member);

        //when
        Seed seed1 = Seed.create(SeedType.STUDY, member, "제목1", "내용1", now);
        seedService.saveSeed(seed1);
        Seed seed2 = Seed.create(SeedType.STUDY, member, "제목2", "내용2", now);
        seedService.saveSeed(seed2);
        Seed seed3 = Seed.create(SeedType.STUDY, member, "제목3", "내용3", now);
        seedService.saveSeed(seed3);

        //when
        Member findMember2 = memberService.findMemberById(saveMemberId);

        em.flush();

        //then
        assertThat(findMember2.getSeedList().size()).isEqualTo(3);
    }

    @Test
    @DisplayName("모든 멤버 조회")
    @Transactional
    void findAll() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Member member = Member.create("greenneuron", "nickname", "password", now, now);
        memberService.saveMember(member);
        Member member2 = Member.create("greenneuron2", "nickname2", "password", now, now);
        memberService.saveMember(member2);

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
        LocalDateTime now = LocalDateTime.now();
        Member member = Member.create("greenneuron", "nickname", "password", now, now);
        Long saveId = memberService.saveMember(member);

        //when
        memberService.removeMember(member);

        //then
        Member findMember = memberService.findMemberById(saveId);
        assertThat(findMember).isNull();
    }

    @Test
    @DisplayName("멤버 수정")
    @Transactional
    public void update() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Member member = Member.create("greenneuron", "nickname", "password", now, now);
        Long saveId = memberService.saveMember(member);
        UpdateMemberDTO updateMemberDTO = new UpdateMemberDTO();
        updateMemberDTO.setId(saveId);
        updateMemberDTO.setUsername("변경된 이름");
        updateMemberDTO.setNickname("변경된 닉네임");

        //when
        memberService.updateMember(updateMemberDTO);

        //then
        Member findMember = memberService.findMemberById(saveId);
        assertThat(findMember.getUsername()).isEqualTo("변경된 이름");
        assertThat(findMember.getNickname()).isEqualTo("변경된 닉네임");
    }

    @Test
    @DisplayName("등록된 시드 리스트를 시드 그룹 리스트로 변환해서 반환")
    public void seedGroupList() {
        //given
        List<Seed> seedList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        Member member = Member.create("greenneuron", "nickname", "password", now, now);
        seedList.add(Seed.create(SeedType.PLAY, member, "제목", "내용", now));
        seedList.add(Seed.create(SeedType.STUDY, member, "제목", "내용", now.minusDays(1)));
        seedList.add(Seed.create(SeedType.READ, member, "제목", "내용", now.minusDays(2)));

        //when
        List<SeedGroup> seedGroups = member.seedGroupList();

        //then
        Assertions.assertThat(seedGroups.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("등록된 시드가 없으면 시드 그룹 리스트를 Empty 리스트로 반환")
    public void seedGroupListReturnEmptyList() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Member member = Member.create("greenneuron", "nickname", "password", now, now);

        //when
        List<SeedGroup> seedGroups = member.seedGroupList();

        //then
        Assertions.assertThat(seedGroups.size()).isEqualTo(0);
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