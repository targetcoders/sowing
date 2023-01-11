package com.targetcoders.sowing.seed.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ToString
@Getter
public class SeedMonthGroup implements Comparable<SeedMonthGroup> {

    private final Month sowingMonth;
    private final List<SeedDayGroup> seedDayGroups = new ArrayList<>();

    public static SeedMonthGroup create(Month month, List<Seed> sameMonthSeeds) {
        return new SeedMonthGroup(month, sameMonthSeeds);
    }

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

    public boolean isEmptySeedDayGroup() {
        return seedDayGroups.isEmpty();
    }

    private void setSeedDayGroups(List<Seed> sameMonthSeeds) {
        boolean[] createdGroup = new boolean[32];
        for (int i = 0; i < sameMonthSeeds.size(); i++) {
            Seed sameMonthSeed = sameMonthSeeds.get(i);
            int day = sameMonthSeed.getSowingDate().getDayOfMonth();
            if (createdGroup[day]) {
                continue;
            }

            createdGroup[day] = true;
            SeedDayGroup seedDayGroup = SeedDayGroup.create(day, sameMonthSeeds);
            seedDayGroups.add(seedDayGroup);
        }
    }

    @Override
    public int compareTo(SeedMonthGroup seedMonthGroup) {
        return sowingMonth.compareTo(seedMonthGroup.sowingMonth) * -1;
    }
}
