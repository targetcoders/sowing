package com.targetcoders.sowing.seed.domain;

import com.targetcoders.sowing.member.domain.GoogleTokens;
import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.member.service.MemberService;
import com.targetcoders.sowing.member.domain.Settings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SeedYearGroupTest {

    @Autowired MemberService memberService;

    @Test
    @DisplayName("SeedYearGroup 생성자에 다른 년도에 등록된 시드가 있는 경우 예외 발생")
    void addDifferentYearSeed() {
        LocalDate dateTime1 = LocalDate.of(2022, 1, 1);
        LocalDate dateTime2 = LocalDate.of(2022, 12, 9);
        LocalDate dateTime3 = LocalDate.of(2021, 12, 9);
        LocalDate dateTime4 = LocalDate.of(2022, 12, 31);
        Member member = Member.create("greenneuron", "nickname", new GoogleTokens("accessToken","refreshToken"),"sowingRefreshToken", dateTime1, dateTime1, Settings.create());
        Seed seed1 = Seed.create(DefaultSeedType.PLAY.toString(), member, "제목1", "내용1", dateTime1);
        Seed seed2 = Seed.create(DefaultSeedType.READ.toString(), member, "제목2", "내용2", dateTime2);
        Seed seed3 = Seed.create(DefaultSeedType.STUDY.toString(), member, "제목3", "내용3", dateTime3);
        Seed seed4 = Seed.create(DefaultSeedType.DATE.toString(), member, "제목4", "내용4", dateTime4);
        List<Seed> seeds = new ArrayList<>();
        seeds.add(seed1);
        seeds.add(seed2);
        seeds.add(seed3);
        seeds.add(seed4);

        Assertions.assertThrows(IllegalArgumentException.class, () -> new SeedYearGroup(2022, seeds));
    }

    @Test
    @DisplayName("SeedYearGroup 생성자 테스트")
    void createSeedYearGroup() {
        //given
        LocalDate dateTime1 = LocalDate.of(2022, 1, 1);
        LocalDate dateTime2 = LocalDate.of(2022, 12, 9);
        LocalDate dateTime3 = LocalDate.of(2022, 12, 9);
        LocalDate dateTime4 = LocalDate.of(2022, 12, 31);
        Member member = Member.create("greenneuron", "nickname", new GoogleTokens("accessToken","refreshToken"),"sowingRefreshToken", dateTime1, dateTime1, Settings.create());
        Seed seed1 = Seed.create(DefaultSeedType.PLAY.toString(), member, "제목1", "내용1", dateTime1);
        Seed seed2 = Seed.create(DefaultSeedType.READ.toString(), member, "제목2", "내용2", dateTime2);
        Seed seed3 = Seed.create(DefaultSeedType.STUDY.toString(), member, "제목3", "내용3", dateTime3);
        Seed seed4 = Seed.create(DefaultSeedType.DATE.toString(), member, "제목4", "내용4", dateTime4);
        List<Seed> seeds = new ArrayList<>();
        seeds.add(seed1);
        seeds.add(seed2);
        seeds.add(seed3);
        seeds.add(seed4);

        //when
        int year = 2022;
        List<Seed> sameYearSeeds = seeds.stream().filter(seed -> year == seed.getSowingDate().getYear()).collect(Collectors.toList());
        SeedYearGroup seedYearGroup = new SeedYearGroup(year, sameYearSeeds);

        //then
        List<SeedMonthGroup> seedMonthGroups = seedYearGroup.getSeedMonthGroups();
        SeedMonthGroup seedMonthGroup1 = seedMonthGroups.stream().filter(smg -> smg.getSowingMonth() == Month.DECEMBER).findFirst().orElse(null);
        SeedMonthGroup seedMonthGroup2 = seedMonthGroups.stream().filter(smg -> smg.getSowingMonth() == Month.FEBRUARY).findFirst().orElse(null);
        SeedMonthGroup seedMonthGroup3 = seedMonthGroups.stream().filter(smg -> smg.getSowingMonth() == Month.JANUARY).findFirst().orElse(null);

        assertThat(seedMonthGroup1).isNotNull();
        assertThat(seedMonthGroup2).isNotNull();
        assertThat(seedMonthGroup3).isNotNull();

        assertThat(seedMonthGroup1.getSeedDayGroups().size()).isEqualTo(2);
        assertThat(seedMonthGroup1.getSeedDayGroups().get(0).getSeeds().size()).isEqualTo(2); // 12월 9일 시드 개수 = 2
        assertThat(seedMonthGroup2.getSeedDayGroups().size()).isEqualTo(0);
        assertThat(seedMonthGroup3.getSeedDayGroups().size()).isEqualTo(1);
    }

}