package com.targetcoders.sowing.seed.dto;

import com.targetcoders.sowing.seed.domain.SeedType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class UpdateSeedDTO {

    private Long id;
    private SeedType type;
    private String title;
    private String content;
    private LocalDate sowingDate;

}
