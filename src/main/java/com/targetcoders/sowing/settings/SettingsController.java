package com.targetcoders.sowing.settings;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingsController {

    @GetMapping("/settings")
    public String settings(Model model) {
        SettingsDTO settingsDTO = new SettingsDTO();
        model.addAttribute("settings", settingsDTO);
        return "settings/settings";
    }
}
