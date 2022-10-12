package com.targetcoders.sowing.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long saveMember(Member member) {
        LocalDateTime now = LocalDateTime.now();
        member.setRegistrationDate(now);
        member.setLastAccessDate(now);
        member.setMemberRole(MemberRole.ROLE_USER);
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return memberRepository.save(member);
    }

    @Transactional
    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Transactional
    public void removeMember(Member member) {
        memberRepository.remove(member);
    }

    @Transactional
    public void updateMember(UpdateMemberDTO updateMemberDTO) {
        memberRepository.updateMember(updateMemberDTO);
    }

    @Transactional
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    @Transactional
    public List<Member> findMemberByUsername(String memberUsername) {
        return memberRepository.findByUsername(memberUsername);
    }

}
