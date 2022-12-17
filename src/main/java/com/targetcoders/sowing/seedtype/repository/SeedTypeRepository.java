package com.targetcoders.sowing.seedtype.repository;

import com.targetcoders.sowing.seedtype.domain.SeedType;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@NoArgsConstructor
public class SeedTypeRepository {

    private EntityManager em;

    @Autowired
    public SeedTypeRepository(EntityManager em) {
        this.em = em;
    }

    @SuppressWarnings("unchecked")
    public Optional<SeedType> findSeedTypeById(String username, Long id) {
        List<SeedType> seedTypes = em.createQuery("select m.settings.seedTypes from Member m where m.username = :username")
                .setParameter("username", username)
                .getResultList();
        return seedTypes.stream().filter(seedType -> Objects.equals(seedType.getId(), id)).findFirst();
    }

    public void saveSeedType(SeedType seedType) {
        em.persist(seedType);
    }
}
