package com.targetcoders.sowing.settings.dao;

import com.targetcoders.sowing.settings.domain.Settings;
import com.targetcoders.sowing.settings.repository.SettingsRepository;
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
