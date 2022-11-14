package com.targetcoders.sowing.controller;

import com.targetcoders.sowing.seed.ILocalDate;
import com.targetcoders.sowing.seed.domain.SeedYearGroup;
import com.targetcoders.sowing.seed.dto.SeedYearGroupsDTO;
import com.targetcoders.sowing.seed.service.SeedGroupService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final SeedGroupService seedGroupService;
    private final ILocalDate localDateTime;

    @GetMapping("/")
    public String home(Authentication authentication, HttpServletResponse response, @RequestParam(name = "year", required = false) Integer year, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            if (year == null) {
                year = localDateTime.now().getYear();
            }
            String email = authentication.getName();
            SeedYearGroup seedYearGroup = seedGroupService.seedYearGroup(year, email);
            SeedYearGroupsDTO seedYearGroupsDTO = new ModelMapper().map(seedYearGroup, SeedYearGroupsDTO.class);
            model.addAttribute("seedYearGroups", seedYearGroupsDTO);
            model.addAttribute("year", year);
        }
        response.setHeader("Cache-Control", "no-cache; no-store; must-revalidate");
        return "home";
    }
}
