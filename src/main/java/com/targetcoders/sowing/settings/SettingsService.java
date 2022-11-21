package com.targetcoders.sowing.settings;

import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.member.domain.SeedType;
import com.targetcoders.sowing.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingsService {

    private final MemberService memberService;

    @Transactional
    public List<SeedType> seedTypes(String username) {
        Member member = memberService.findMemberByUsername(username);
        return member.getSettings().getSeedTypes();
    }

}
