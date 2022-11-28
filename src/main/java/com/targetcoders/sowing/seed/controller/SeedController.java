package com.targetcoders.sowing.seed.controller;

import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.member.domain.SeedType;
import com.targetcoders.sowing.member.service.MemberService;
import com.targetcoders.sowing.seed.domain.Seed;
import com.targetcoders.sowing.seed.dto.SeedFormDTO;
import com.targetcoders.sowing.seed.dto.UpdateSeedDTO;
import com.targetcoders.sowing.seed.service.SeedService;
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
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class SeedController {

    private final SeedService seedService;
    private final MemberService memberService;

    @GetMapping("/seeds/new")
    public String seedForm(Model model, Authentication authentication) {
        Member member = memberService.findMemberByUsername(authentication.getName());
        model.addAttribute("seedTypes", member.getSettings().getSeedTypes());
        return "seeds/createSeedForm";
    }

    @PostMapping("/seeds/new")
    public String create(SeedFormDTO seedFormDTO) throws NotFoundException {
        seedService.saveSeed(seedFormDTO);
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
        SeedFormDTO seedFormDTO = new SeedFormDTO();
        seedFormDTO.setTitle(seed.getTitle());
        seedFormDTO.setSelectType(seed.getType());
        seedFormDTO.setContent(seed.getContent());
        seedFormDTO.setUsername(seed.getMember().getUsername());
        seedFormDTO.setSowingDate(seed.getSowingDate());
        Member member = memberService.findMemberByUsername(authentication.getName());
        List<SeedType> seedTypes = member.getSettings().getSeedTypes();
        seedFormDTO.setTypeList(seedTypes.stream().map(SeedType::getName).collect(Collectors.toList()));
        seedFormDTO.setId(id);
        model.addAttribute("seedForm", seedFormDTO);
        return "seeds/editSeedForm";
    }

    @PostMapping("seeds/{id}/edit")
    public String update(@ModelAttribute("form") SeedFormDTO seedFormDTO) {
        ModelMapper modelMapper = new ModelMapper();
        UpdateSeedDTO updateSeedDTO = modelMapper.map(seedFormDTO, UpdateSeedDTO.class);
        seedService.updateSeed(updateSeedDTO);
        return "redirect:/";
    }

    @ResponseBody
    @DeleteMapping("seeds/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        seedService.removeSeedById(id);
        return new ResponseEntity<>("delete success, seedId="+id, HttpStatus.OK);
    }
}
