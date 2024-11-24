package com.targetcoders.sowing.member;

import static org.assertj.core.api.Assertions.assertThat;

import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.member.dto.CreateMemberDTO;
import com.targetcoders.sowing.member.dto.UpdateMemberDTO;
import com.targetcoders.sowing.member.service.MemberService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberTest {

    @Autowired MemberService memberService;

    @Test
    @DisplayName("모든 멤버 조회")
    @Transactional
    void findAll() {
        //given
        CreateMemberDTO createMemberDTO1 = new CreateMemberDTO("greenneuron", "nickname", "accessToken", "refreshToken");
        memberService.saveMember(createMemberDTO1);
        CreateMemberDTO createMemberDTO2 = new CreateMemberDTO("greenneuron2", "nickname2", "accessToken2", "refreshToken");
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
        CreateMemberDTO createMemberDTO1 = new CreateMemberDTO("greenneuron", "nickname", "accessToken", "refreshToken");
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
        CreateMemberDTO createMemberDTO1 = new CreateMemberDTO("greenneuron", "nickname",  "accessToken", "refreshToken");
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
}
