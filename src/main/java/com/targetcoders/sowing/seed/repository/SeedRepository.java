package com.targetcoders.sowing.seed.repository;

import com.targetcoders.sowing.seed.dto.UpdateSeedDTO;
import com.targetcoders.sowing.seed.domain.Seed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class SeedRepository {

    private final EntityManager em;

    public void save(Seed seed) {
        em.persist(seed);
    }

    public Seed findById(Long id) {
        return em.find(Seed.class, id);
    }

    public void removeById(Long id) {
        em.remove(findById(id));
    }

    public void updateSeed(UpdateSeedDTO updateSeedDto) {
        Seed seed = em.find(Seed.class, updateSeedDto.getId());
        seed.update(updateSeedDto);
    }

}
