package com.targetcoders.sowing.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.targetcoders.sowing.common.LoginConstants;
import com.targetcoders.sowing.login.GoogleUserInfoDTO;
import com.targetcoders.sowing.login.LoginService;
import com.targetcoders.sowing.member.GoogleTokens;
import com.targetcoders.sowing.member.Member;
import com.targetcoders.sowing.member.MemberRole;
import com.targetcoders.sowing.security.JwtTokenProvider;
import javassist.tools.web.BadHttpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {

    private final LoginService loginService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/login/google")
    public String login(Principal principal) {
        if (principal != null) {
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
        GoogleTokens oauthGoogleTokens = loginService.googleTokens(code);
        GoogleUserInfoDTO googleUserInfoDTO = loginService.googleUserInfo(oauthGoogleTokens.getAccessToken());
        Member member = loginService.joinMember(googleUserInfoDTO, oauthGoogleTokens.getAccessToken(), oauthGoogleTokens.getRefreshToken());
        String refreshToken = jwtTokenProvider.createRefreshToken(googleUserInfoDTO.getEmail(), member.getMemberRole());
        response.setHeader("set-cookie", "REFRESH-TOKEN=" + refreshToken + "; path=/; max-age=86400; HttpOnly; SameSite=Strict");
        return "redirect:/";
    }

    @ResponseBody
    @PostMapping("/login/token")
    public ResponseEntity<String> accessToken(@RequestHeader("Authorization") String refreshToken) throws BadHttpRequest {
        if (!jwtTokenProvider.isValidateToken(refreshToken)) {
            return new ResponseEntity<>("Invalid Token", HttpStatus.BAD_REQUEST);
        }
        String accessToken = jwtTokenProvider.createAccessToken(jwtTokenProvider.getUserPk(refreshToken), MemberRole.valueOf(jwtTokenProvider.getMemberRole(refreshToken)));
        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(null);
            response.setHeader("set-cookie", "REFRESH-TOKEN=");
        }
        return "redirect:/";
    }
}
