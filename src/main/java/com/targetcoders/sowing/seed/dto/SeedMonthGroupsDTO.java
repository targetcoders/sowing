package com.targetcoders.sowing.seed.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Month;
import java.util.List;

@Getter @Setter @ToString
public class SeedMonthGroupsDTO {
    private Month month;
    private List<SeedDayGroupsDTO> seedDayGroups;
}
