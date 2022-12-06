package com.targetcoders.sowing.seed.dto;

import com.targetcoders.sowing.member.domain.SeedType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class SeedDTO {
    private Long id;
    private SeedType type;
    private String title;
}
