package com.targetcoders.sowing.settings.service;

import com.targetcoders.sowing.member.domain.SeedType;
import com.targetcoders.sowing.settings.dao.SeedTypeDao;
import org.springframework.stereotype.Service;

@Service
public class SeedTypeService {

    private final SeedTypeDao seedTypeDao;

    public SeedTypeService(SeedTypeDao seedTypeDao) {
        this.seedTypeDao = seedTypeDao;
    }

    public SeedType findSeedTypeBydId(Long id) {
        return seedTypeDao.findSeedTypeById(id);
    }

}
