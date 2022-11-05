package com.targetcoders.sowing.controller;

import com.targetcoders.sowing.seed.service.SeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final SeedService seedService;

    @GetMapping("/")
    public String home(Authentication authentication, HttpServletResponse response, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            model.addAttribute("seedGroupList", seedService.findSeedGroups(username));
        }
        response.setHeader("Cache-Control", "no-cache; no-store; must-revalidate");
        return "home";
    }
}
