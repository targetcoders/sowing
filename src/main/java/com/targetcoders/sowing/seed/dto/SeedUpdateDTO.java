package com.targetcoders.sowing.seed.dto;

import com.targetcoders.sowing.seedtype.domain.SeedType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeedUpdateDTO {

    private Long id;
    private SeedType seedType;
    private String title;
    private String content;
    private LocalDate sowingDate;

}
