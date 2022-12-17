package com.targetcoders.sowing.seedtype.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.targetcoders.sowing.seedtype.dto.AddSeedTypeDTO;
import com.targetcoders.sowing.seedtype.dto.SeedTypeRenameDTO;
import com.targetcoders.sowing.seedtype.exception.SeedTypeDuplicateException;
import com.targetcoders.sowing.seedtype.service.SeedTypeService;
import com.targetcoders.sowing.settings.dto.SettingsDTO;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class SeedTypeController {

    private final SeedTypeService seedTypeService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/settings")
    public String settings(Authentication authentication, Model model) throws NotFoundException {
        String username = authentication.getName();
        SettingsDTO settingsDTO = new SettingsDTO();
        settingsDTO.setSeedTypes(seedTypeService.seedTypes(username));
        model.addAttribute("settings", settingsDTO);
        return "settings/settings";
    }

    @ResponseBody
    @PostMapping("/settings/seedtype/new")
    public ResponseEntity<String> addSeedType(Authentication authentication, @RequestBody String json) throws JsonProcessingException {
        AddSeedTypeDTO addSeedTypeDTO = objectMapper.readValue(json, AddSeedTypeDTO.class);
        try {
            seedTypeService.addSeedType(authentication.getName(), addSeedTypeDTO);
        } catch(SeedTypeDuplicateException e) {
            return new ResponseEntity<>("이미 존재하는 시드 타입입니다.", HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>("가입되지 않은 회원의 요청입니다.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/settings/seedtype")
    public ResponseEntity<String> removeSeedType(Authentication authentication, @RequestParam("seedTypeName") String seedTypeName) throws NotFoundException {
        boolean hasSeedType = seedTypeService.hasSeedType(authentication.getName(), seedTypeName);

        if (hasSeedType) {
            return new ResponseEntity<>("해당 시드 타입으로 등록된 시드가 존재하여 삭제할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }
        seedTypeService.removeSeedType(authentication.getName(), seedTypeName);
        return new ResponseEntity<>(seedTypeName, HttpStatus.OK);
    }

    @PostMapping("/seedtypes/rename")
    public ResponseEntity<String> renameSeedType(Authentication authentication, @RequestBody SeedTypeRenameDTO seedTypeRenameDTO) throws NotFoundException {
        seedTypeService.renameSeedType(authentication.getName(), seedTypeRenameDTO);
        return new ResponseEntity<>("rename success", HttpStatus.OK);
    }

}
