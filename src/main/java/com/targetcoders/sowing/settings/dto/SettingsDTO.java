package com.targetcoders.sowing.settings.dto;

import com.targetcoders.sowing.seedtype.domain.SeedType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
public class SettingsDTO {
    private List<SeedType> seedTypes;
}
