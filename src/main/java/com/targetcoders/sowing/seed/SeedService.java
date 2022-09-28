package com.targetcoders.sowing.seed;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeedService {

    private final SeedRepository seedRepository;

    @Transactional
    public void saveSeed(Seed seed) {
        seedRepository.save(seed);
    }

    @Transactional
    public Seed findById(Long id) {
        return seedRepository.findById(id);
    }
}
