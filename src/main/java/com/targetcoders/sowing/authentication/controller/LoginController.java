package com.targetcoders.sowing.authentication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.targetcoders.sowing.common.LoginConstants;
import com.targetcoders.sowing.login.GoogleUserInfoDTO;
import com.targetcoders.sowing.login.LoginService;
import com.targetcoders.sowing.member.GoogleTokens;
import com.targetcoders.sowing.member.MemberRole;
import com.targetcoders.sowing.member.MemberService;
import com.targetcoders.sowing.authentication.service.HeaderSetService;
import com.targetcoders.sowing.authentication.domain.JwtTokenProvider;
import com.targetcoders.sowing.authentication.service.TokenUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {

    private final LoginService loginService;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
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
    public String loginCallback(@RequestParam("code") String code, @RequestParam("scope") String scope, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        System.out.println("code = " + code + ", scope = " + scope);
        GoogleTokens oauthGoogleTokens = loginService.googleTokens(code);
        GoogleUserInfoDTO googleUserInfoDTO = loginService.googleUserInfo(oauthGoogleTokens.getAccessToken());

        String email = googleUserInfoDTO.getEmail();
        String sowingRefreshToken = jwtTokenProvider.createRefreshToken(email, MemberRole.ROLE_USER);
        if (memberService.findMemberByUsername(email).size() == 0) {
            loginService.joinMember(googleUserInfoDTO, oauthGoogleTokens.getAccessToken(), oauthGoogleTokens.getRefreshToken(), sowingRefreshToken);
        } else {
            tokenUpdateService.updateAllTokens(email, oauthGoogleTokens.getAccessToken(), oauthGoogleTokens.getRefreshToken(), sowingRefreshToken);
        }

        String sowingAccessToken = jwtTokenProvider.createAccessToken(email, MemberRole.ROLE_USER, sowingRefreshToken);
        headerSetService.setAccessTokenCookie(response, sowingAccessToken, "900");
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(null);
            tokenUpdateService.updateSowingRefreshToken(authentication.getName(), "");
            headerSetService.setAccessTokenCookie(response, "", "0");
        }
        return "redirect:/";
    }
}
