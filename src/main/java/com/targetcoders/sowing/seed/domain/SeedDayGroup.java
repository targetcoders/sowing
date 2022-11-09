package com.targetcoders.sowing.seed.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class SeedDayGroup implements Comparable<SeedDayGroup> {

    private Integer day;
    private final List<Seed> seeds = new ArrayList<>();

    public static SeedDayGroup create(Integer day) {
        return new SeedDayGroup(day);
    }

    public void addSeed(Seed seed) {
        seeds.add(seed);
    }

    @Override
    public int compareTo(SeedDayGroup seedDayGroup) {
        int compareResult = day.compareTo(seedDayGroup.day);
        return Integer.compare(0, compareResult);
    }
}
