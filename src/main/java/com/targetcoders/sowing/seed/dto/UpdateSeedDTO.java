package com.targetcoders.sowing.seed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSeedDTO {

    private Long id;
    private String selectType;
    private String title;
    private String content;
    private LocalDate sowingDate;

}
