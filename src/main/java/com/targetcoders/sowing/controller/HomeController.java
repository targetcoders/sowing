package com.targetcoders.sowing.controller;

import com.targetcoders.sowing.member.Member;
import com.targetcoders.sowing.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    @GetMapping("/")
    public String home(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            List<Member> memberList = memberService.findMemberByUsername(username);
            assert memberList.size() == 1;
            model.addAttribute("seedGroupList", memberList.get(0).seedGroupList());
        }
        return "home";
    }
}
