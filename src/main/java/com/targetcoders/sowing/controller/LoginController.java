package com.targetcoders.sowing.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.targetcoders.sowing.common.LoginConstants;
import com.targetcoders.sowing.login.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login/google")
    public String login(Principal principal) {
        if (principal != null) {
            return "redirect:/";
        }
        String googleOauthLoginRequestURI = LoginConstants.REQUEST_URI +
                "?client_id=" + LoginConstants.CLIENT_ID +
                "&scope=" + LoginConstants.SCOPE +
                "&redirect_uri=" + LoginConstants.REDIRECT_URI +
                "&response_type=" + LoginConstants.RESPONSE_TYPE;
        return "redirect:" + googleOauthLoginRequestURI;
    }

    @GetMapping("/login/google/callback")
    public String loginCallback(@RequestParam("code") String code, @RequestParam("scope") String scope) throws JsonProcessingException {
        System.out.println("code = " + code + ", scope = " + scope);

        String accessToken = loginService.accessToken(code);
        GoogleUserInfoDTO googleUserInfoDTO = loginService.googleUserInfo(accessToken);
        loginService.joinMember(googleUserInfoDTO, accessToken);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/home";
    }
}
