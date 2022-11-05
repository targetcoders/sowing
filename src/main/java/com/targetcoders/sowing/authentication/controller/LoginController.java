package com.targetcoders.sowing.authentication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.targetcoders.sowing.authentication.LoginConstants;
import com.targetcoders.sowing.authentication.domain.JwtToken;
import com.targetcoders.sowing.authentication.dto.GoogleUserInfoDTO;
import com.targetcoders.sowing.authentication.service.*;
import com.targetcoders.sowing.member.CreateMemberDTO;
import com.targetcoders.sowing.member.GoogleTokens;
import com.targetcoders.sowing.member.MemberRole;
import com.targetcoders.sowing.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {

    private final GoogleLoginService googleLoginService;
    private final MemberService memberService;
    private final JwtTokenService jwtTokenService;
    private final TokenUpdateService tokenUpdateService;
    private final HeaderSetService headerSetService;

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
        System.out.println("code = " + code + ", scope = " + scope);
        GoogleTokens oauthGoogleTokens = googleLoginService.googleTokens(code);
        GoogleUserInfoDTO googleUserInfoDTO = googleLoginService.googleUserInfo(oauthGoogleTokens.getAccessToken());

        String email = googleUserInfoDTO.getEmail();
        JwtToken sowingRefreshToken = jwtTokenService.createRefreshToken(email, MemberRole.ROLE_USER);
        if (memberService.isExistMember(email)) {
            tokenUpdateService.updateAllTokens(email, oauthGoogleTokens.getAccessToken(), oauthGoogleTokens.getRefreshToken(), sowingRefreshToken);
        } else {
            CreateMemberDTO createMemberDTO = new CreateMemberDTO(googleUserInfoDTO.getEmail(), googleUserInfoDTO.getName(), oauthGoogleTokens.getAccessToken(), oauthGoogleTokens.getRefreshToken(), sowingRefreshToken);
            memberService.saveMember(createMemberDTO);
        }

        JwtToken sowingAccessToken = jwtTokenService.createAccessToken(email, MemberRole.ROLE_USER, sowingRefreshToken);
        headerSetService.setAccessTokenCookie(response, sowingAccessToken.toString(), "900");
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(Authentication authentication, HttpServletResponse response) {
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(null);
            tokenUpdateService.updateSowingRefreshToken(authentication.getName(), "");
            headerSetService.setAccessTokenCookie(response, "", "0");
        }
        return "redirect:/";
    }
}
