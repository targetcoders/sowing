package com.targetcoders.sowing.seed.controller;

import com.targetcoders.sowing.seed.domain.SeedTypeCountResult;
import com.targetcoders.sowing.seed.dto.SeedOverviewDTO;
import com.targetcoders.sowing.seed.service.SeedOverviewService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SeedOverviewController {

    private final SeedOverviewService seedOverviewService;

    @GetMapping("/overview")
    public String overview(Authentication authentication, Model model) throws NotFoundException {
        if (authentication.isAuthenticated()) {
            SeedTypeCountResult seedTypeCounterResult = seedOverviewService.countSeeds(authentication.getName());
            SeedOverviewDTO seedOverviewDTO = new SeedOverviewDTO();
            seedOverviewDTO.setSeedTypeCounterResult(seedTypeCounterResult);
            model.addAttribute("seedOverview", seedOverviewDTO);
        }
        return "seeds/overview";
    }
}
