package com.targetcoders.sowing.seed.dto;

import com.targetcoders.sowing.seed.domain.SeedType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UpdateSeedDTO {

    private final Long id;
    private final SeedType type;
    private final String title;
    private final String content;
    private final LocalDateTime sowingDate;

}
