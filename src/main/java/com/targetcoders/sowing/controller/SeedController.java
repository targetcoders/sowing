package com.targetcoders.sowing.controller;

import com.targetcoders.sowing.seed.SeedForm;
import com.targetcoders.sowing.seed.SeedService;
import com.targetcoders.sowing.seed.SeedType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class SeedController {

    private final SeedService seedService;

    @GetMapping("/seeds/new")
    public String seedForm(Model model) {
        List<SeedType> typeList = Arrays.stream(SeedType.values()).collect(Collectors.toList());
        model.addAttribute("typeList", typeList);
        return "createSeedForm";
    }

    @PostMapping("/seeds/new")
    public String create(SeedForm seedForm) {
        seedService.saveSeed(seedForm);
        return "redirect:/";
    }

}
