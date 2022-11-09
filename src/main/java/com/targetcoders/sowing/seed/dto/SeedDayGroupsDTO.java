package com.targetcoders.sowing.seed.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class SeedDayGroupsDTO {
    private Integer day;
    private List<SeedDTO> seeds;
}
