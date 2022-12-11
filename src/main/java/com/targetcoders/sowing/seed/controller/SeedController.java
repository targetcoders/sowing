package com.targetcoders.sowing.seed.controller;

import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.seedtype.domain.SeedType;
import com.targetcoders.sowing.member.service.MemberService;
import com.targetcoders.sowing.seed.domain.Seed;
import com.targetcoders.sowing.seed.dto.SeedCreateDTO;
import com.targetcoders.sowing.seed.dto.SeedEditDTO;
import com.targetcoders.sowing.seed.dto.SeedEditFormDTO;
import com.targetcoders.sowing.seed.dto.SeedUpdateDTO;
import com.targetcoders.sowing.seed.service.SeedService;
import com.targetcoders.sowing.seedtype.service.SeedTypeService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SeedController {

    private final SeedService seedService;
    private final MemberService memberService;
    private final SeedTypeService seedTypeService;

    @GetMapping("/seeds/new")
    public String seedForm(Model model, Authentication authentication) {
        Member member = memberService.findMemberByUsername(authentication.getName());
        model.addAttribute("seedTypes", member.getSettings().getSeedTypes());
        return "seeds/createSeedForm";
    }

    @PostMapping("/seeds/new")
    public String create(SeedCreateDTO seedCreateDTO) throws NotFoundException {
        seedService.saveSeed(seedCreateDTO);
        return "redirect:/";
    }

    @GetMapping("/seeds/{id}")
    public String seedFind(Model model, @PathVariable("id") Long id) {
        Seed findSeed = seedService.findSeedById(id);
        model.addAttribute("seed", findSeed);
        return "seeds/seed";
    }

    @GetMapping("seeds/{id}/edit")
    public String update(Model model, Authentication authentication, @PathVariable("id") Long id) {
        Seed seed = seedService.findSeedById(id);
        SeedEditFormDTO seedEditFormDTO = new SeedEditFormDTO();
        seedEditFormDTO.setTitle(seed.getTitle());
        seedEditFormDTO.setSelectedType(seed.getSeedType());
        seedEditFormDTO.setContent(seed.getContent());
        seedEditFormDTO.setUsername(seed.getMember().getUsername());
        seedEditFormDTO.setSowingDate(seed.getSowingDate());
        Member member = memberService.findMemberByUsername(authentication.getName());
        List<SeedType> seedTypes = member.getSettings().getSeedTypes();
        seedEditFormDTO.setTypeList(seedTypes);
        seedEditFormDTO.setId(id);
        model.addAttribute("seedEditFormDto", seedEditFormDTO);
        return "seeds/editSeedForm";
    }

    @PostMapping("seeds/{id}/edit")
    public String update(@ModelAttribute("form") SeedEditDTO seedEditDTO) {
        ModelMapper modelMapper = new ModelMapper();
        SeedUpdateDTO seedUpdateDTO = modelMapper.map(seedEditDTO, SeedUpdateDTO.class);
        Long seedTypeId = seedEditDTO.getSeedTypeId();
        seedUpdateDTO.setSeedType(seedTypeService.findSeedTypeBydId(seedTypeId));
        seedService.updateSeed(seedUpdateDTO);
        return "redirect:/";
    }

    @ResponseBody
    @DeleteMapping("seeds/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        seedService.removeSeedById(id);
        return new ResponseEntity<>("delete success, seedId="+id, HttpStatus.OK);
    }
}
