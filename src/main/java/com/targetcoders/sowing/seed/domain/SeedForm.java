package com.targetcoders.sowing.seed.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO;

@Getter @Setter
public class SeedForm {

    private Long id;
    private String username;
    private String title;
    private List<DefaultSeedType> typeList;
    private DefaultSeedType selectType;
    private String content;
    private @DateTimeFormat(iso = ISO.DATE) LocalDate sowingDate;

}
