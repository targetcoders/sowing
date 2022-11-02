package com.targetcoders.sowing.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member saveMember(CreateMemberDTO createMemberDTO) {
        String accessToken = createMemberDTO.getGoogleAccessToken();
        String refreshToken = createMemberDTO.getGoogleRefreshToken();
        GoogleTokens googleTokens = new GoogleTokens(accessToken, refreshToken);
        String sowingRefreshToken = createMemberDTO.getSowingRefreshToken().toString();

        Member member = new Member();
        member.setUsername(createMemberDTO.getEmail());
        member.setGoogleTokens(googleTokens);
        member.setSowingRefreshToken(sowingRefreshToken);
        member.setNickname(createMemberDTO.getNickname());
        member.setMemberRole(MemberRole.ROLE_USER);
        LocalDateTime now = LocalDateTime.now();
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
    public boolean isExistMember(String memberUsername) {
        List<Member> members = memberRepository.findByUsername(memberUsername);
        return members.size() > 0;
    }

}
