package com.targetcoders.sowing.controller;

import com.targetcoders.sowing.member.Member;
import com.targetcoders.sowing.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;

	@GetMapping("/member/new")
	public String signup() {
		return "/createMemberForm";
	}
	
	@PostMapping("/member/new")
	public String create(Member member) {
		memberService.saveMember(member);
		return "redirect:/";
	}
}