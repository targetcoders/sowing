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
        for (Month eachMonth : Month.values()) {
            List<Seed> sameMonthSeeds = sameMonthSeeds(sameYearSeeds, eachMonth);
            SeedMonthGroup seedMonthGroup = SeedMonthGroup.create(eachMonth, sameMonthSeeds);
            if (!seedMonthGroup.isEmptySeedDayGroup()) {
                seedMonthGroups.add(seedMonthGroup);
            }
        }
    }

    private List<Seed> sameMonthSeeds(List<Seed> sameYearSeeds, Month eachMonth) {
        List<Seed> sameMonthSeeds = new ArrayList<>();
        for (Seed sameYearSeed : sameYearSeeds) {
            if (sameYearSeed.getSowingDate().getMonth() == eachMonth) {
                sameMonthSeeds.add(sameYearSeed);
            }
        }
        return sameMonthSeeds;
    }

    @Override
    public int compareTo(SeedYearGroup seedYearGroup) {
        return year.compareTo(seedYearGroup.year);
    }
}
