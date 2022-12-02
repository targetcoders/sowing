package com.targetcoders.sowing.seed.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ToString @Getter
public class SeedYearGroup implements Comparable<SeedYearGroup> {

    private final Integer year;
    private final List<SeedMonthGroup> seedMonthGroups = new ArrayList<>();

    public SeedYearGroup(int year, List<Seed> sameYearSeeds) {
        this.year = year;
        for (Seed seed : sameYearSeeds) {
            int eachYear = seed.getSowingDate().getYear();
            if (eachYear != year) {
                throw new IllegalArgumentException("예상 Year=" + year + ", 실제 Year=" + eachYear);
            }
        }
        setSeedMonthGroups(sameYearSeeds);
        Collections.sort(seedMonthGroups);
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
            if (!seedMonthGroup.getSeedDayGroups().isEmpty()) {
                seedMonthGroups.add(seedMonthGroup);
            }
        }
    }

    @Override
    public int compareTo(SeedYearGroup seedYearGroup) {
        return year.compareTo(seedYearGroup.year);
    }
}
