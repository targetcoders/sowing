package com.targetcoders.sowing.settings;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SettingsController {

    private final SettingsService settingsService;

    @GetMapping("/settings")
    public String settings(Authentication authentication, Model model) {
        String username = authentication.getName();
        SettingsDTO settingsDTO = new SettingsDTO();
        settingsDTO.setSeedTypes(settingsService.seedTypes(username));
        model.addAttribute("settings", settingsDTO);
        return "settings/settings";
    }
}
