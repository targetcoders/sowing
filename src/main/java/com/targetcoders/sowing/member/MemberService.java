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
    public Member saveMember(CreateMemberDTO createMemberDTO) {
        LocalDateTime now = LocalDateTime.now();
        Member member = new Member();
        member.setUsername(createMemberDTO.getEmail());
        String accessToken = passwordEncoder.encode(createMemberDTO.getAccessToken());
        String refreshToken = createMemberDTO.getRefreshToken();
        if (refreshToken != null) {
             refreshToken = passwordEncoder.encode(refreshToken);
        }
        Tokens tokens = new Tokens(accessToken, refreshToken);
        member.setTokens(tokens);
        member.setNickname(createMemberDTO.getNickname());
        member.setMemberRole(MemberRole.ROLE_USER);
        member.setRegistrationDate(now);
        member.setLastAccessDate(now);

        Member findMember = findMember(member.getUsername());
        if (findMember.getId() != null) {
            return findMember;
        }

        memberRepository.save(member);
        return member;
    }

    private Member findMember(String username) {
        List<Member> findMembers = memberRepository.findByUsername(username);
        if (!findMembers.isEmpty()) {
            return findMembers.get(0);
        }
        Member defaultMember = new Member();
        defaultMember.setNickname("guest");
        return defaultMember;
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
