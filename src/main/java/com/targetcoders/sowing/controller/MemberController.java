package com.targetcoders.sowing.controller;

import com.targetcoders.sowing.member.Member;
import com.targetcoders.sowing.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;

	@GetMapping("/members/new")
	public String signup(Principal principal) {
		if (principal != null) {
			return "redirect:/";
		}
		return "/createMemberForm";
	}
	
	@PostMapping("/members/new")
	public String create(Member member) {
		memberService.saveMember(member);
		return "redirect:/";
	}
}