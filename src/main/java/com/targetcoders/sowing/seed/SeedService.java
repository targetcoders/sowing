package com.targetcoders.sowing.seed;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeedService {

    private final SeedRepository seedRepository;

    @Transactional
    public Long saveSeed(Seed seed) {
        seedRepository.save(seed);
        return seed.getId();
    }

    @Transactional
    public Seed findSeedById(Long seedId) {
        return seedRepository.findById(seedId);
    }

    @Transactional
    public void removeSeedById(Long seedId) {
        seedRepository.removeById(seedId);
    }

    @Transactional
    public void updateSeed(UpdateSeedDTO updateSeedDto) {
        seedRepository.updateSeed(updateSeedDto);
    }
}
