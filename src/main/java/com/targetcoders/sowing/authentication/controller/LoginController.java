package com.targetcoders.sowing.authentication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.targetcoders.sowing.authentication.LoginConstants;
import com.targetcoders.sowing.authentication.dto.GoogleUserInfoDTO;
import com.targetcoders.sowing.authentication.service.GoogleLoginService;
import com.targetcoders.sowing.authentication.service.SessionService;
import com.targetcoders.sowing.authentication.service.TokenUpdateService;
import com.targetcoders.sowing.member.domain.GoogleJwt;
import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.member.dto.CreateMemberDTO;
import com.targetcoders.sowing.member.service.MemberService;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {

    private final GoogleLoginService googleLoginService;
    private final MemberService memberService;
    private final TokenUpdateService tokenUpdateService;
    private final SessionService sessionService;

    @GetMapping("/login/google")
    public String login(Authentication authentication) {
        if (authentication != null) {
            return "redirect:/";
        }

        String googleOauthLoginRequestURI = LoginConstants.REQUEST_URI +
                "?client_id=" + LoginConstants.CLIENT_ID +
                "&scope=" + LoginConstants.SCOPE +
                "&redirect_uri=" + LoginConstants.REDIRECT_URI +
                "&response_type=" + LoginConstants.RESPONSE_TYPE + "&access_type=offline";
        return "redirect:" + googleOauthLoginRequestURI;
    }

    @GetMapping("/login/google/callback")
    public String loginCallback(@RequestParam("code") String code, @RequestParam("scope") String scope, HttpServletResponse response) throws JsonProcessingException {
        log.info("loginCallback > code = {}, scope = {}", code, scope);
        GoogleJwt oauthGoogleJwt = googleLoginService.googleLogin(code);
        GoogleUserInfoDTO googleUserInfoDTO = googleLoginService.googleUserInfo(oauthGoogleJwt.getAccessToken());

        String email = googleUserInfoDTO.getEmail();
        Member member = memberService.findMemberByUsername(email);
        if (member != null) {
            tokenUpdateService.updateAllTokens(email, oauthGoogleJwt.getAccessToken(), oauthGoogleJwt.getRefreshToken());
        } else {
            CreateMemberDTO createMemberDTO = new CreateMemberDTO(googleUserInfoDTO.getEmail(), googleUserInfoDTO.getName(), oauthGoogleJwt.getAccessToken(), oauthGoogleJwt.getRefreshToken());
            memberService.saveMember(createMemberDTO);
        }
        UUID uuid = UUID.randomUUID();
        sessionService.putUsername(uuid.toString(), email);
        response.setHeader("Set-Cookie", "JSESSIONID=" + uuid + "; Path=/;");

        log.info("redirect:/ after login callback");
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(Authentication authentication, HttpServletResponse response) {
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(null);
            response.setHeader("Set-Cookie", "JSESSIONID=" + "; Max-Age=" + 0 + "; Path=/;");
        }
        return "redirect:/";
    }
}
