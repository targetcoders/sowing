package com.targetcoders.sowing.seed.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@RequiredArgsConstructor
public class UpdateSeedDTO {

    private final Long id;
    private final String type;
    private final String title;
    private final String content;
    private final LocalDate sowingDate;

}
