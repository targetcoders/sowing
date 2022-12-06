package com.targetcoders.sowing.seed.repository;

import com.targetcoders.sowing.seed.dto.SeedUpdateDTO;
import com.targetcoders.sowing.seed.domain.Seed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

    public void updateSeed(SeedUpdateDTO seedUpdateDto) {
        Seed seed = em.find(Seed.class, seedUpdateDto.getId());
        seed.update(seedUpdateDto);
    }

    @SuppressWarnings("unchecked")
    public List<Seed> findByUsername(String username) {
        return em.createQuery("select s from Seed s where s.member.username = :username")
                .setParameter("username", username)
                .getResultList();
    }
}
