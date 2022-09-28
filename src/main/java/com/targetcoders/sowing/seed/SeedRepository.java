package com.targetcoders.sowing.seed;

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
}
