package com.targetcoders.sowing.settings.repository;

import com.targetcoders.sowing.member.domain.SeedType;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@NoArgsConstructor
public class SeedTypeRepository {

    private EntityManager em;

    @Autowired
    public SeedTypeRepository(EntityManager em) {
        this.em = em;
    }

    public SeedType findSeedTypeById(Long id) {
        return em.find(SeedType.class, id);
    }

    public void saveSeedType(SeedType seedType) {
        em.persist(seedType);
    }
}
