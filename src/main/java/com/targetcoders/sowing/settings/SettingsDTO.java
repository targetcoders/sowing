package com.targetcoders.sowing.settings;

import com.targetcoders.sowing.member.domain.SeedType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter @Getter
public class SettingsDTO {
    private List<SeedType> seedTypes;
}
