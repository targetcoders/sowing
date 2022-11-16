package com.targetcoders.sowing.seed.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@ToString @Getter
public class SeedMonthGroup implements Comparable<SeedMonthGroup> {

    private final Month sowingMonth;
    private final List<SeedDayGroup> seedDayGroups = new ArrayList<>();

    public SeedMonthGroup(Month sowingMonth, List<Seed> sameMonthSeeds) {
        this.sowingMonth = sowingMonth;
        setSeedDayGroups(sameMonthSeeds);
    }

    private void setSeedDayGroups(List<Seed> sameMonthSeeds) {
        boolean[] check = new boolean[32];
        for (int i=0; i<sameMonthSeeds.size(); i++) {
            Seed seed = sameMonthSeeds.get(i);
            int day = seed.getSowingDate().getDayOfMonth();
            if(check[day]) {
                continue;
            }

            check[day] = true;
            SeedDayGroup seedDayGroup = new SeedDayGroup(day);
            for (int j=i; j< sameMonthSeeds.size(); j++) {
                Seed otherSeed = sameMonthSeeds.get(j);
                int otherDay = otherSeed.getSowingDate().getDayOfMonth();
                if (day == otherDay) {
                    seedDayGroup.addSeed(otherSeed);
                }
            }
            seedDayGroups.add(seedDayGroup);
        }
    }

    @Override
    public int compareTo(SeedMonthGroup seedMonthGroup) {
        int compareResult = sowingMonth.compareTo(seedMonthGroup.sowingMonth);
        return Integer.compare(0, compareResult);
    }
}