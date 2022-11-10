package com.targetcoders.sowing.member.dao;

import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.member.repository.MemberRepository;
import com.targetcoders.sowing.member.dto.UpdateMemberDTO;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberDao {

    private final MemberRepository memberRepository;

    public Member findByUsername(String username) throws NotFoundException {
        List<Member> members = memberRepository.findByUsername(username);
        if (members.isEmpty()) {
            throw new NotFoundException("Member is not founded, username="+username);
        }
        return members.get(0);
    }

    public void save(Member member) {
        memberRepository.save(member);
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public void remove(Member member) {
        memberRepository.remove(member);
    }

    public void updateMember(UpdateMemberDTO updateMemberDTO) {
        memberRepository.updateMember(updateMemberDTO);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}
