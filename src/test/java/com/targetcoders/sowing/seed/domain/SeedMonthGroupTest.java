package com.targetcoders.sowing.seed.domain;

import com.targetcoders.sowing.member.domain.GoogleTokens;
import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.seedtype.domain.SeedType;
import com.targetcoders.sowing.settings.domain.Settings;
import com.targetcoders.sowing.seedtype.domain.DefaultSeedType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SeedMonthGroupTest {

    @Test
    @DisplayName("다른 월에 등록된 시드가 있는 경우 예외 발생")
    void anOtherMonthIsContained() {
        LocalDate dateTime1 = LocalDate.of(2022, 1, 1);
        LocalDate dateTime2 = LocalDate.of(2022, 12, 9);
        LocalDate dateTime3 = LocalDate.of(2022, 12, 9);
        LocalDate dateTime4 = LocalDate.of(2022, 12, 31);
        Member member = Member.create("greenneuron", "nickname", new GoogleTokens("accessToken","refreshToken"),"sowingRefreshToken", dateTime1, dateTime1, Settings.create());
        Seed seed1 = Seed.create(new SeedType(DefaultSeedType.PLAY.toString()), member, "제목1", "내용1", dateTime1);
        Seed seed2 = Seed.create(new SeedType(DefaultSeedType.READ.toString()), member, "제목2", "내용2", dateTime2);
        Seed seed3 = Seed.create(new SeedType(DefaultSeedType.STUDY.toString()), member, "제목3", "내용3", dateTime3);
        Seed seed4 = Seed.create(new SeedType(DefaultSeedType.DATE.toString()), member, "제목4", "내용4", dateTime4);
        List<Seed> seeds = new ArrayList<>();
        seeds.add(seed1);
        seeds.add(seed2);
        seeds.add(seed3);
        seeds.add(seed4);

        Assertions.assertThrows(IllegalArgumentException.class, () -> new SeedMonthGroup(Month.JANUARY, seeds));
    }

    @Test
    @DisplayName("월별 내림차순 정렬")
    void sortSeedMonthGroupDesc() {
        List<SeedMonthGroup> seedMonthGroups = new ArrayList<>();
        seedMonthGroups.add(new SeedMonthGroup(Month.JANUARY, new ArrayList<>()));
        seedMonthGroups.add(new SeedMonthGroup(Month.DECEMBER, new ArrayList<>()));
        seedMonthGroups.add(new SeedMonthGroup(Month.JULY, new ArrayList<>()));

        Collections.sort(seedMonthGroups);

        assertThat(seedMonthGroups.get(0).getSowingMonth()).isEqualTo(Month.DECEMBER);
        assertThat(seedMonthGroups.get(1).getSowingMonth()).isEqualTo(Month.JULY);
        assertThat(seedMonthGroups.get(2).getSowingMonth()).isEqualTo(Month.JANUARY);
    }

}