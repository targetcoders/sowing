package com.targetcoders.sowing.member.repository;

import com.targetcoders.sowing.member.domain.Settings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class SettingsRepository {

    private final EntityManager em;

    public Settings saveSettings(Settings settings) {
        em.persist(settings);
        return settings;
    }
}
