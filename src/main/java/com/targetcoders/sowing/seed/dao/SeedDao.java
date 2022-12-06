package com.targetcoders.sowing.seed.dao;

import com.targetcoders.sowing.seed.domain.Seed;
import com.targetcoders.sowing.seed.dto.SeedUpdateDTO;
import com.targetcoders.sowing.seed.repository.SeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SeedDao {

    private final SeedRepository seedRepository;

    public void saveSeed(Seed seed){
        seedRepository.save(seed);
    }

    public Seed findSeedById(Long id) {
        return seedRepository.findById(id);
    }

    public void removeSeedById(Long id) {
        seedRepository.removeById(id);
    }

    public void updateSeed(SeedUpdateDTO seedUpdateDTO) {
        seedRepository.updateSeed(seedUpdateDTO);
    }

    public List<Seed> findSeeds(String username) {
        return seedRepository.findByUsername(username);
    }

    public List<Seed> findYearSeeds(Integer year, String username) {
        List<Seed> seeds = seedRepository.findByUsername(username);
        return seeds.stream()
                .filter(seed -> seed.getSowingDate().getYear() == year)
                .collect(Collectors.toList());
    }
}
