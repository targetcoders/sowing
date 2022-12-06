package com.targetcoders.sowing.seed.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class SeedEditDTO {

    private Long id;
    private String username;
    private String title;
    private Long seedTypeId;
    private String content;
    private @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate sowingDate;

}
