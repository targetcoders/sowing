package com.targetcoders.sowing.seed.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SeedDayGroupTest {

    @Test
    @DisplayName("시드 그룹 리스트를 날짜 기준 내림차순 정렬")
    public void sortedSeedGroupList() {
        //given
        List<SeedDayGroup> seedDayGroups = new ArrayList<>();
        SeedDayGroup seedDayGroup1 = new SeedDayGroup(LocalDateTime.now().toLocalDate().getDayOfMonth());
        SeedDayGroup seedDayGroup2 = new SeedDayGroup(LocalDateTime.now().minusDays(1).toLocalDate().getDayOfMonth());
        SeedDayGroup seedDayGroup3 = new SeedDayGroup(LocalDateTime.now().minusDays(2).toLocalDate().getDayOfMonth());
        seedDayGroups.add(seedDayGroup3);
        seedDayGroups.add(seedDayGroup2);
        seedDayGroups.add(seedDayGroup1);

        //when
        Collections.sort(seedDayGroups);

        //then
        assertThat(seedDayGroups.get(0)).isEqualTo(seedDayGroup1);
        assertThat(seedDayGroups.get(1)).isEqualTo(seedDayGroup2);
        assertThat(seedDayGroups.get(2)).isEqualTo(seedDayGroup3);
    }
}
