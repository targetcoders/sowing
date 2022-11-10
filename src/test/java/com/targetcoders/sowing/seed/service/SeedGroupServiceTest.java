package com.targetcoders.sowing.seed.service;

import com.targetcoders.sowing.member.domain.GoogleTokens;
import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.member.repository.MemberRepository;
import com.targetcoders.sowing.seed.domain.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SeedGroupServiceTest {

    @Autowired SeedGroupService seedGroupService;
    @Autowired SeedService seedService;
    @Autowired MemberRepository memberRepository;

    @Test
    @DisplayName("특정 년도의 SeedYearGroup 조회 및 정렬 테스트")
    @Transactional
    void deepSortSeedYearGroup() {
        LocalDateTime dateTime1 = LocalDateTime.of(LocalDate.of(2022, 12, 3), LocalTime.of(20,0));
        LocalDateTime dateTime2 = LocalDateTime.of(LocalDate.of(2022, 12, 9), LocalTime.of(20,0));
        LocalDateTime dateTime3 = LocalDateTime.of(LocalDate.of(2022, 12, 1), LocalTime.of(21,0));
        LocalDateTime dateTime4 = LocalDateTime.of(LocalDate.of(2022, 12, 31), LocalTime.of(23,0));

        LocalDateTime dateTime5 = LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(20,0));
        LocalDateTime dateTime6 = LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(20,0));
        LocalDateTime dateTime7 = LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(21,0));
        LocalDateTime dateTime8 = LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(23,0));
        LocalDateTime dateTime9 = LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(23,0));
        LocalDateTime dateTime10 = LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(20,0));
        LocalDateTime dateTime11 = LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(20,0));
        LocalDateTime dateTime12 = LocalDateTime.of(LocalDate.of(2022, 1, 1), LocalTime.of(21,0));

        Member member = Member.create("greenneuron", "nickname", new GoogleTokens("accessToken","refreshToken"),"sowingRefreshToken", dateTime1, dateTime1);
        memberRepository.save(member);

        Seed seed1 = Seed.create(SeedType.PLAY, member, "제목1", "내용1", dateTime1);
        Seed seed2 = Seed.create(SeedType.READ, member, "제목2", "내용2", dateTime2);
        Seed seed3 = Seed.create(SeedType.STUDY, member, "제목3", "내용3", dateTime3);
        Seed seed4 = Seed.create(SeedType.DATE, member, "제목4", "내용4", dateTime4);

        Seed seed5 = Seed.create(SeedType.PLAY, member, "제목1", "내용1", dateTime5);
        Seed seed6 = Seed.create(SeedType.READ, member, "제목2", "내용2", dateTime6);
        Seed seed7 = Seed.create(SeedType.STUDY, member, "제목3", "내용3", dateTime7);
        Seed seed8 = Seed.create(SeedType.DATE, member, "제목4", "내용4", dateTime8);
        Seed seed9 = Seed.create(SeedType.PLAY, member, "제목1", "내용1", dateTime9);
        Seed seed10 = Seed.create(SeedType.READ, member, "제목2", "내용2", dateTime10);
        Seed seed11 = Seed.create(SeedType.STUDY, member, "제목3", "내용3", dateTime11);
        Seed seed12 = Seed.create(SeedType.DATE, member, "제목4", "내용4", dateTime12);

        seedService.saveSeed(seed1);
        seedService.saveSeed(seed2);
        seedService.saveSeed(seed3);
        seedService.saveSeed(seed4);
        seedService.saveSeed(seed5);
        seedService.saveSeed(seed6);
        seedService.saveSeed(seed7);
        seedService.saveSeed(seed8);
        seedService.saveSeed(seed9);
        seedService.saveSeed(seed10);
        seedService.saveSeed(seed11);
        seedService.saveSeed(seed12);

        //when
        int year = 2022;
        SeedYearGroup seedYearGroup = seedGroupService.seedYearGroup(year, "greenneuron");

        //then
        //월 오름차순 정렬
        List<SeedMonthGroup> monthSortTestGroup = seedYearGroup.getSeedMonthGroups();
        Assertions.assertThat(monthSortTestGroup.get(0).getSowingMonth()).isEqualTo(Month.JANUARY);
        Assertions.assertThat(monthSortTestGroup.get(1).getSowingMonth()).isEqualTo(Month.FEBRUARY);
        Assertions.assertThat(monthSortTestGroup.get(2).getSowingMonth()).isEqualTo(Month.MARCH);
        Assertions.assertThat(monthSortTestGroup.get(3).getSowingMonth()).isEqualTo(Month.APRIL);
        Assertions.assertThat(monthSortTestGroup.get(4).getSowingMonth()).isEqualTo(Month.MAY);
        Assertions.assertThat(monthSortTestGroup.get(5).getSowingMonth()).isEqualTo(Month.JUNE);
        Assertions.assertThat(monthSortTestGroup.get(6).getSowingMonth()).isEqualTo(Month.JULY);
        Assertions.assertThat(monthSortTestGroup.get(7).getSowingMonth()).isEqualTo(Month.AUGUST);
        Assertions.assertThat(monthSortTestGroup.get(8).getSowingMonth()).isEqualTo(Month.SEPTEMBER);
        Assertions.assertThat(monthSortTestGroup.get(9).getSowingMonth()).isEqualTo(Month.OCTOBER);
        Assertions.assertThat(monthSortTestGroup.get(10).getSowingMonth()).isEqualTo(Month.NOVEMBER);
        Assertions.assertThat(monthSortTestGroup.get(11).getSowingMonth()).isEqualTo(Month.DECEMBER);

        //일 내림차순 정렬
        SeedMonthGroup daySortTestGroup = monthSortTestGroup.stream().filter(smg -> smg.getSowingMonth() == Month.DECEMBER).findFirst().orElse(null);
        assertThat(daySortTestGroup).isNotNull();
        List<SeedDayGroup> daySortTestList = daySortTestGroup.getSeedDayGroups();
        assertThat(daySortTestList.get(0).getDay()).isEqualTo(31);
        assertThat(daySortTestList.get(1).getDay()).isEqualTo(9);
        assertThat(daySortTestList.get(2).getDay()).isEqualTo(3);
        assertThat(daySortTestList.get(3).getDay()).isEqualTo(1);

        //시드 타입 order 기준 오름차순 정렬
        SeedMonthGroup seedTypeSortTestGroup = monthSortTestGroup.stream().filter(smg -> smg.getSowingMonth() == Month.JANUARY).findFirst().orElse(null);
        assertThat(seedTypeSortTestGroup).isNotNull();
        List<Seed> seedTypeSortTestList = seedTypeSortTestGroup.getSeedDayGroups().get(0).getSeeds();
        assertThat(seedTypeSortTestList.get(0).getType()).isEqualTo(SeedType.READ);
        assertThat(seedTypeSortTestList.get(1).getType()).isEqualTo(SeedType.READ);
        assertThat(seedTypeSortTestList.get(2).getType()).isEqualTo(SeedType.PLAY);
        assertThat(seedTypeSortTestList.get(3).getType()).isEqualTo(SeedType.PLAY);
        assertThat(seedTypeSortTestList.get(4).getType()).isEqualTo(SeedType.STUDY);
        assertThat(seedTypeSortTestList.get(5).getType()).isEqualTo(SeedType.STUDY);
        assertThat(seedTypeSortTestList.get(6).getType()).isEqualTo(SeedType.DATE);
        assertThat(seedTypeSortTestList.get(7).getType()).isEqualTo(SeedType.DATE);
    }
}