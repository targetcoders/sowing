package com.targetcoders.sowing.seed.controller;

import com.targetcoders.sowing.seed.domain.Seed;
import com.targetcoders.sowing.seed.domain.SeedForm;
import com.targetcoders.sowing.seed.domain.SeedType;
import com.targetcoders.sowing.seed.dto.UpdateSeedDTO;
import com.targetcoders.sowing.seed.service.SeedService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
        return "seeds/createSeedForm";
    }

    @PostMapping("/seeds/new")
    public String create(SeedForm seedForm) throws NotFoundException {
        seedService.saveSeed(seedForm);
        return "redirect:/";
    }

    @GetMapping("/seeds/{id}")
    public String seedFind(Model model, @PathVariable("id") Long id) {
        Seed findSeed = seedService.findSeedById(id);
        model.addAttribute("seed", findSeed);
        return "seeds/seed";
    }

    @GetMapping("seeds/{id}/edit")
    public String update(Model model, @PathVariable("id") Long id) {
        Seed seed = seedService.findSeedById(id);
        SeedForm seedForm = new SeedForm();
        seedForm.setTitle(seed.getTitle());
        seedForm.setSelectType(seed.getType());
        seedForm.setContent(seed.getContent());
        seedForm.setUsername(seed.getMember().getUsername());
        model.addAttribute("seed", seedForm);
        List<SeedType> typeList = Arrays.stream(SeedType.values()).collect(Collectors.toList());
        seedForm.setTypeList(typeList);
        seedForm.setId(id);
        return "seeds/editSeedForm";
    }

    @PostMapping("seeds/{id}/edit")
    public String update(@ModelAttribute("form") SeedForm seedForm) {
        UpdateSeedDTO updateSeedDTO = new UpdateSeedDTO(
                seedForm.getId(),
                seedForm.getSelectType(),
                seedForm.getTitle(),
                seedForm.getContent(),
                LocalDateTime.now());
        seedService.updateSeed(updateSeedDTO);
        return "redirect:/";
    }

    @ResponseBody
    @DeleteMapping("seeds/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        System.out.println("SeedController.delete");
        seedService.removeSeedById(id);
        return new ResponseEntity<>("delete success, seedId="+id, HttpStatus.OK);
    }
}
