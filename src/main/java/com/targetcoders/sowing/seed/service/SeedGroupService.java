package com.targetcoders.sowing.seed.service;

import com.targetcoders.sowing.seed.domain.SeedDayGroup;
import com.targetcoders.sowing.seed.domain.SeedMonthGroup;
import com.targetcoders.sowing.seed.domain.SeedYearGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeedGroupService {

    private final SeedService seedService;

    @Transactional
    public SeedYearGroup seedYearGroup(int year, String email) {
        SeedYearGroup seedYearGroup = new SeedYearGroup(year, seedService.findSeedsByUsername(email));
        deepSortSeedYearGroup(seedYearGroup);
        return seedYearGroup;
    }

    private void deepSortSeedYearGroup(SeedYearGroup seedYearGroup) {
        List<SeedMonthGroup> seedMonthGroups = seedYearGroup.getSeedMonthGroups();
        for (SeedMonthGroup seedMonthGroup : seedMonthGroups) {
            List<SeedDayGroup> seedDayGroups = seedMonthGroup.getSeedDayGroups();
            Collections.sort(seedDayGroups);
            for (SeedDayGroup seedDayGroup : seedDayGroups) {
                Collections.sort(seedDayGroup.getSeeds());
            }
        }
    }
}
