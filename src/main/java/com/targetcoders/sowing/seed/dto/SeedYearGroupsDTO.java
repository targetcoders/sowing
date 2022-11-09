package com.targetcoders.sowing.seed.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class SeedYearGroupsDTO {
    private Integer year;
    private List<SeedMonthGroupsDTO> seedMonthGroups;
}
