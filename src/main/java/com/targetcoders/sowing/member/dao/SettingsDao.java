package com.targetcoders.sowing.member.dao;

import com.targetcoders.sowing.member.domain.Settings;
import com.targetcoders.sowing.member.repository.SettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SettingsDao {

    private final SettingsRepository settingsRepository;

    public Settings saveSettings(Settings settings) {
        return settingsRepository.saveSettings(settings);
    }
}
