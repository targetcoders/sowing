package com.targetcoders.sowing.seed.domain;

import com.targetcoders.sowing.member.domain.GoogleTokens;
import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.member.domain.Settings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

class SeedMonthGroupTest {

    @Test
    @DisplayName("다른 월에 등록된 시드가 있는 경우 예외 발생")
    void test() {
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

        Assertions.assertThrows(IllegalArgumentException.class, () -> new SeedMonthGroup(Month.JANUARY, seeds));
    }

}