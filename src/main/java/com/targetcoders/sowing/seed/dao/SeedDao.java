package com.targetcoders.sowing.seed.dao;

import com.targetcoders.sowing.seed.dto.UpdateSeedDTO;
import com.targetcoders.sowing.seed.domain.Seed;
import com.targetcoders.sowing.seed.repository.SeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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

    public void updateSeed(UpdateSeedDTO updateSeedDTO) {
        seedRepository.updateSeed(updateSeedDTO);
    }

    public List<Seed> findSeedsByUsername(String username) {
        return seedRepository.findByUsername(username);
    }
}
