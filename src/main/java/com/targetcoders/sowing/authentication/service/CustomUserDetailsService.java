package com.targetcoders.sowing.authentication.service;

import com.targetcoders.sowing.authentication.domain.SecurityMember;
import com.targetcoders.sowing.member.Member;
import com.targetcoders.sowing.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private final MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<Member> memberList = memberRepository.findByUsername(username);
		if (memberList.isEmpty()) {
			throw new UsernameNotFoundException(username);
		}
		return new SecurityMember(memberList.get(0));
	}
}