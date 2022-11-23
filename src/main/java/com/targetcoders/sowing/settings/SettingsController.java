package com.targetcoders.sowing.settings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class SettingsController {

    private final SettingsService settingsService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/settings")
    public String settings(Authentication authentication, Model model) throws NotFoundException {
        String username = authentication.getName();
        SettingsDTO settingsDTO = new SettingsDTO();
        settingsDTO.setSeedTypes(settingsService.seedTypes(username));
        model.addAttribute("settings", settingsDTO);
        return "settings/settings";
    }

    @ResponseBody
    @PostMapping("/settings/seedtype/new")
    public ResponseEntity<String> addSeedType(Authentication authentication, @RequestBody String json) throws JsonProcessingException {
        AddSeedTypeDTO addSeedTypeDTO = objectMapper.readValue(json, AddSeedTypeDTO.class);
        String seedTypeName;
        try {
            seedTypeName = settingsService.addSeedType(authentication.getName(), addSeedTypeDTO);
        } catch(SeedTypeDuplicateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(seedTypeName, HttpStatus.OK);
    }

}
