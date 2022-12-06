package com.targetcoders.sowing.seed.dto;

import com.targetcoders.sowing.member.domain.SeedType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class SeedEditFormDTO {

    private Long id;
    private String username;
    private String title;
    private List<SeedType> typeList;
    private SeedType selectedType;
    private String content;
    private @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate sowingDate;

}
