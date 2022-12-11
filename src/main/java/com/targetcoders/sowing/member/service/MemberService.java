package com.targetcoders.sowing.member.service;

import com.targetcoders.sowing.member.dao.MemberDao;
import com.targetcoders.sowing.settings.dao.SettingsDao;
import com.targetcoders.sowing.member.domain.GoogleTokens;
import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.settings.domain.Settings;
import com.targetcoders.sowing.member.dto.CreateMemberDTO;
import com.targetcoders.sowing.member.dto.UpdateMemberDTO;
import com.targetcoders.sowing.seed.ILocalDate;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberDao memberDao;
    private final SettingsDao settingsDao;
    private final ILocalDate localDateTime;

    @Transactional
    public Member saveMember(CreateMemberDTO createMemberDTO) {
        String accessToken = createMemberDTO.getGoogleAccessToken();
        String refreshToken = createMemberDTO.getGoogleRefreshToken();
        String sowingRefreshToken = createMemberDTO.getSowingRefreshToken().toString();
        LocalDate now = localDateTime.now();
        GoogleTokens googleTokens = new GoogleTokens(accessToken, refreshToken);
        Settings settings = settingsDao.saveSettings(Settings.create());
        Member member = Member.create(createMemberDTO.getEmail(), createMemberDTO.getNickname(), googleTokens, sowingRefreshToken, now, now, settings);
        memberDao.save(member);
        return member;
    }

    @Transactional
    public Member findMemberById(Long memberId) {
        return memberDao.findById(memberId);
    }

    @Transactional
    public void removeMember(Member member) {
        memberDao.remove(member);
    }

    @Transactional
    public void updateMember(UpdateMemberDTO updateMemberDTO) {
        memberDao.updateMember(updateMemberDTO);
    }

    @Transactional
    public List<Member> findAllMembers() {
        return memberDao.findAll();
    }

    public Member findMemberByUsername(String email) {
        try {
            return memberDao.findByUsername(email);
        } catch (NotFoundException e) {
            return null;
        }
    }
}
