package com.targetcoders.sowing.member.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class SecurityMember extends User {
	private static final String ROLE_PREFIX = "ROLE_";
	private static final long serialVersionUID = 1L;
	
	public SecurityMember(Member member) {
		super(member.getUsername(), member.getGoogleJwt().getAccessToken(), makeGrantedAuthority(member.getMemberRole()));
	}
	
	private static List<GrantedAuthority> makeGrantedAuthority(MemberRole role){
		List<GrantedAuthority> list = new ArrayList<>();
		list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));
		return list;
	}
}