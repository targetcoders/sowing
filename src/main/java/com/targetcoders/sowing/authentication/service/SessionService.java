package com.targetcoders.sowing.authentication.service;

import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.member.service.MemberService;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class SessionService {

  private final ConcurrentHashMap<String, String> sessionIdUsernameMap = new ConcurrentHashMap<>();

  public void putUsername(String sessionId, String email) {
    if (sessionId != null && email != null) {
      sessionIdUsernameMap.put(sessionId, email);
    }
  }

  public Member getLoggedInMember(String jSessionId, MemberService memberService) {
    String username = sessionIdUsernameMap.get(jSessionId);
    if (username == null) {
      return null;
    }
    return memberService.findMemberByUsername(username);
  }
}
