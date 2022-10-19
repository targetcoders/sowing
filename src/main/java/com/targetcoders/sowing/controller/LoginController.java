package com.targetcoders.sowing.controller;

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
public class LoginController {

    private static final String REQUEST_URI = "https://accounts.google.com/o/oauth2/v2/auth";
    private static final String CLIENT_ID = "341020820476-isdn8vj1e925suj59io78sabdhtukmq0.apps.googleusercontent.com";
    private static final String REDIRECT_URI = "http://localhost:8080/login/google/callback";
    private static final String SCOPE = "https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile+openid";
    private static final String RESPONSE_TYPE = "code";

    @GetMapping("/login/google")
    public String login(Principal principal) {
        if (principal != null) {
            return "redirect:/";
        }
        String googleOauthLoginRequestURI = REQUEST_URI +
                "?client_id=" + CLIENT_ID +
                "&scope=" + SCOPE +
                "&redirect_uri=" + REDIRECT_URI +
                "&response_type=" + RESPONSE_TYPE;
        return "redirect:" + googleOauthLoginRequestURI;
    }

    @GetMapping("/login/google/callback")
    public String loginCallback(@RequestParam("code") String code, @RequestParam("scope") String scope) {
        System.out.println("code = " + code + ", scope = " + scope);
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
