package com.targetcoders.sowing.settings.domain;

import com.targetcoders.sowing.seedtype.domain.DefaultSeedType;
import com.targetcoders.sowing.seedtype.domain.SeedType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

class SettingsTest {

    @Test
    @DisplayName("Settings 객체가 생성될 때는 DefaultSeedType 리스트를 초기화한다.")
    void initDefaultSeedType() {
        Settings settings = new Settings();
        List<String> expectedSeedTypeNames = new ArrayList<>();
        for (DefaultSeedType seedType : DefaultSeedType.values()) {
            expectedSeedTypeNames.add(seedType.name());
        }

        List<String> result = settings.getSeedTypes().stream().map(SeedType::getName).collect(Collectors.toList());

        Assertions.assertThat(result).containsAll(expectedSeedTypeNames);
    }

}