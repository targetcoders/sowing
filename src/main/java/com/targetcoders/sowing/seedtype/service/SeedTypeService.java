package com.targetcoders.sowing.seedtype.service;

import com.targetcoders.sowing.seedtype.dao.SeedTypeDao;
import com.targetcoders.sowing.seedtype.domain.SeedType;
import com.targetcoders.sowing.seedtype.dto.AddSeedTypeDTO;
import com.targetcoders.sowing.seedtype.dto.SeedTypeRenameDTO;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SeedTypeService {

    private final SeedTypeDao seedTypeDao;

    public SeedTypeService(SeedTypeDao seedTypeDao) {
        this.seedTypeDao = seedTypeDao;
    }

    public SeedType findSeedTypeBydId(Long id) {
        return seedTypeDao.findSeedTypeById(id);
    }

    @Transactional
    public boolean hasSeedType(String username, String seedTypeName) {
        return seedTypeDao.hasSeedType(username, seedTypeName);
    }

    @Transactional
    public List<SeedType> seedTypes(String username) throws NotFoundException {
        return seedTypeDao.seedTypes(username);
    }

    @Transactional
    public void renameSeedType(SeedTypeRenameDTO seedTypeRenameDTO) {
        SeedType seedType = seedTypeDao.findSeedTypeById(seedTypeRenameDTO.getSeedTypeId());
        seedType.rename(seedTypeRenameDTO.getNewSeedTypeName());
    }

    @Transactional
    public void addSeedType(String username, AddSeedTypeDTO addSeedTypeDTO) throws NotFoundException {
        seedTypeDao.addSeedType(username, addSeedTypeDTO);
    }

    @Transactional
    public void removeSeedType(String username, String seedTypeName) throws NotFoundException {
        seedTypeDao.removeSeedType(username, seedTypeName);
    }

}
