package com.targetcoders.sowing.seed.domain;

import com.targetcoders.sowing.member.domain.SeedType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class SeedTypeCountResultTest {

    private List<SeedType> seedTypes;
    private List<Seed> seeds;

    @BeforeEach
    public void init() {
        seedTypes = new ArrayList<>();
        seedTypes.add(new SeedType("공부"));
        seedTypes.add(new SeedType("독서"));
        seedTypes.add(new SeedType("휴식"));

        seeds = new ArrayList<>();
        seeds.add(Seed.create(new SeedType("공부"), null, "title", "content", null));
        seeds.add(Seed.create(new SeedType("독서"), null, "title", "content", null));
        seeds.add(Seed.create(new SeedType("휴식"), null, "title", "content", null));
    }

    @Test
    @DisplayName("시드 타입의 총 개수를 반환")
    void total() {
        //given
        SeedTypeCountResult seedTypeCounterResult = new SeedTypeCountResult(seedTypes, seeds);

        //when
        long result = seedTypeCounterResult.total();

        //then
        assertThat(result).isEqualTo(3);
    }

    @Test
    @DisplayName("시드 타입 이름 Set을 반환")
    void seedTypeNameSet() {
        //given
        SeedTypeCountResult seedTypeCounterResult = new SeedTypeCountResult(seedTypes, seeds);

        //when
        Set<String> result = seedTypeCounterResult.seedTypeNameSet();

        //then
        Set<String> seedTypeNameSet = new HashSet<>();
        for (SeedType seedType : seedTypes) {
            seedTypeNameSet.add(seedType.getName());
        }
        assertThat(result).isEqualTo(seedTypeNameSet);
    }

    @Test
    @DisplayName("입력으로 주어진 시드 타입에 해당하는 시드의 개수를 반환")
    void getCount() {
        //given
        SeedTypeCountResult seedTypeCounterResult = new SeedTypeCountResult(seedTypes, seeds);

        //when
        Long result1 = seedTypeCounterResult.getCount("공부");
        Long result2 = seedTypeCounterResult.getCount("독서");
        Long result3 = seedTypeCounterResult.getCount("휴식");
        Long result4 = seedTypeCounterResult.getCount("데이트");

        //then
        assertThat(result1).isEqualTo(1);
        assertThat(result2).isEqualTo(1);
        assertThat(result3).isEqualTo(1);
        assertThat(result4).isEqualTo(0);
    }

}