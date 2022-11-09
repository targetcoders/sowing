package com.targetcoders.sowing.controller;

import com.targetcoders.sowing.seed.ILocalDateTime;
import com.targetcoders.sowing.seed.domain.SeedYearGroup;
import com.targetcoders.sowing.seed.dto.SeedYearGroupsDTO;
import com.targetcoders.sowing.seed.service.SeedGroupService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final SeedGroupService seedGroupService;
    private final ILocalDateTime localDateTime;

    @GetMapping("/")
    public String home(Authentication authentication, HttpServletResponse response, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            SeedYearGroup seedYearGroup = seedGroupService.seedThisYearGroup(localDateTime.now().getYear(), email);
            SeedYearGroupsDTO seedYearGroupsDTO = new ModelMapper().map(seedYearGroup, SeedYearGroupsDTO.class);
            model.addAttribute("seedYearGroups", seedYearGroupsDTO);
        }
        response.setHeader("Cache-Control", "no-cache; no-store; must-revalidate");
        return "home";
    }
}
