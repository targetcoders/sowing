package com.targetcoders.sowing.seed.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SeedDayGroupTest {

    @Test
    @DisplayName("일별 내림차순 정렬")
    public void sortedSeedGroupList() {
        //given
        List<SeedDayGroup> seedDayGroups = new ArrayList<>();
        SeedDayGroup seedDayGroup1 = new SeedDayGroup(11);
        SeedDayGroup seedDayGroup2 = new SeedDayGroup(30);
        SeedDayGroup seedDayGroup3 = new SeedDayGroup(20);
        seedDayGroups.add(seedDayGroup1);
        seedDayGroups.add(seedDayGroup2);
        seedDayGroups.add(seedDayGroup3);

        //when
        Collections.sort(seedDayGroups);

        //then
        assertThat(seedDayGroups.get(0).getDay()).isEqualTo(30);
        assertThat(seedDayGroups.get(1).getDay()).isEqualTo(20);
        assertThat(seedDayGroups.get(2).getDay()).isEqualTo(11);
    }
}
