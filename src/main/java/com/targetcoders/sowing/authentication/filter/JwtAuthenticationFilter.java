package com.targetcoders.sowing.authentication.filter;

import com.targetcoders.sowing.authentication.exception.InvalidSessionIdException;
import com.targetcoders.sowing.authentication.service.SessionService;
import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.member.service.MemberService;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final SessionService sessionService;
    private final UserDetailsService userDetailsService;
    private final MemberService memberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jSessionId = getSessionId(request);
        log.info("JwtAuthenticationFilter > requestURI={}, JSESSIONID={}", request.getRequestURI(),
            jSessionId);

        Member member = sessionService.getLoggedInMember(jSessionId, memberService);
        if (isPassRequest(request, member)) {
            log.debug("pass JwtAuthenticationFilter > requestURI={}", request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        if (member == null) {
          log.error("this sessionId is not logged in to Google. sessionId={}", jSessionId);
            throw new InvalidSessionIdException(
                "not found Member, sessionId=" + jSessionId);
        }

        log.info("setAuthentication > member={}", member);
        SecurityContextHolder.getContext().setAuthentication(
            authentication(member.getUsername(), member.getGoogleJwt().toString()));

        filterChain.doFilter(request, response);
    }

    private static String getSessionId(HttpServletRequest request) {
        log.info(Arrays.toString(request.getCookies()));
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("JSESSIONID"))
                .findFirst().map(Cookie::getValue).orElse("");
        }
        return "";
    }

    private boolean isPassRequest(HttpServletRequest request, Member member) {
        return (member == null && request.getRequestURI().equals("/")) ||
                (member == null && request.getRequestURI().startsWith("/login/google")) ||
                request.getRequestURI().startsWith("/css/") ||
                request.getRequestURI().startsWith("/js/") ||
                request.getRequestURI().startsWith("/images/") ||
                request.getRequestURI().startsWith("/error");
    }

    private Authentication authentication(String username, String googleRefreshToken) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, googleRefreshToken, userDetails.getAuthorities());
    }
}
