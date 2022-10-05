package com.targetcoders.sowing.member;

import com.targetcoders.sowing.seed.Seed;
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
        Member member = Member.create("greenneuron", "nickname", now, now);
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
        Member member = Member.create("greenneuron", "nickname", now, now);
        memberService.saveMember(member);
        Member member2 = Member.create("greenneuron2", "nickname2", now, now);
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
        Member member = Member.create("greenneuron", "nickname", now, now);
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
        Member member = Member.create("greenneuron", "nickname", now, now);
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

}