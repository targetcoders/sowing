package com.targetcoders.sowing.seed.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ToString @Getter
public class SeedMonthGroup implements Comparable<SeedMonthGroup> {

    private final Month sowingMonth;
    private final List<SeedDayGroup> seedDayGroups = new ArrayList<>();

    public SeedMonthGroup(Month sowingMonth, List<Seed> sameMonthSeeds) {
        this.sowingMonth = sowingMonth;
        for (Seed seed : sameMonthSeeds) {
            Month month = seed.getSowingDate().getMonth();
            if (month != sowingMonth) {
                throw new IllegalArgumentException("예상 Month=" + sowingMonth + ", 실제 Month=" + month);
            }
        }
        setSeedDayGroups(sameMonthSeeds);
        Collections.sort(seedDayGroups);
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
        return sowingMonth.compareTo(seedMonthGroup.sowingMonth) * -1;
    }
}
