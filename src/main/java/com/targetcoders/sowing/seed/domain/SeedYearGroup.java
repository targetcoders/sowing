package com.targetcoders.sowing.seed.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@ToString @Getter
public class SeedYearGroup implements Comparable<SeedYearGroup> {

    private final Integer year;
    private final List<SeedMonthGroup> seedMonthGroups = new ArrayList<>();

    public SeedYearGroup(int year, List<Seed> seeds) {
        this.year = year;
        setSeedMonthGroups(seeds);
    }

    private void setSeedMonthGroups(List<Seed> sameYearSeeds) {
        for (Month month : Month.values()) {
            List<Seed> sameMonthSeeds = new ArrayList<>();
            for (Seed filteredSeed : sameYearSeeds) {
                if (filteredSeed.getSowingDate().getMonth() == month) {
                    sameMonthSeeds.add(filteredSeed);
                }
            }
            SeedMonthGroup seedMonthGroup = new SeedMonthGroup(month, sameMonthSeeds);
            seedMonthGroups.add(seedMonthGroup);
        }
    }

    @Override
    public int compareTo(SeedYearGroup seedYearGroup) {
        return year.compareTo(seedYearGroup.year);
    }
}
