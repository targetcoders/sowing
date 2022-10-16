package com.targetcoders.sowing.seed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class SeedGroup implements Comparable<SeedGroup> {

    private LocalDate sowingDate;
    private List<Seed> seedList;

    public static SeedGroup create(LocalDate groupDate) {
        return new SeedGroup(groupDate, new ArrayList<>());
    }

    public void addSeed(Seed seed) {
        seedList.add(seed);
    }

    @Override
    public int compareTo(SeedGroup o) {
        int compareResult = sowingDate.compareTo(o.sowingDate);
        return Integer.compare(0, compareResult);
    }
}
