package com.targetcoders.sowing.seed.dto;

import com.targetcoders.sowing.seed.domain.DefaultSeedType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class SeedDTO {
    private Long id;
    private DefaultSeedType type;
    private String title;
}
