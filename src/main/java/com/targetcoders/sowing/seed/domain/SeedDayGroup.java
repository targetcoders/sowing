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

    public static SeedDayGroup create(Integer day, List<Seed> sameMonthSeeds) {
        SeedDayGroup result = new SeedDayGroup(day);
        for (Seed sameMonthSeed : sameMonthSeeds) {
            int eachDay = sameMonthSeed.getSowingDate().getDayOfMonth();
            if (day == eachDay) {
                result.addSeed(sameMonthSeed);
            }
        }
        return result;
    }

    private void addSeed(Seed seed) {
        seeds.add(seed);
    }

    @Override
    public int compareTo(SeedDayGroup seedDayGroup) {
        return day.compareTo(seedDayGroup.day) *-1;
    }
}
