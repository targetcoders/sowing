package com.targetcoders.sowing.member.service;

import com.targetcoders.sowing.member.repository.MemberRepository;
import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.member.domain.SecurityMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private final MemberRepository memberRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<Member> memberList = memberRepository.findByUsername(username);
		if (memberList.isEmpty()) {
			throw new UsernameNotFoundException(username);
		}
		return new SecurityMember(memberList.get(0));
	}
}