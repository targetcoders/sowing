package com.targetcoders.sowing.seedtype.service;

import com.targetcoders.sowing.seedtype.dao.SeedTypeDao;
import com.targetcoders.sowing.seedtype.domain.SeedType;
import com.targetcoders.sowing.seedtype.dto.AddSeedTypeDTO;
import com.targetcoders.sowing.seedtype.dto.SeedTypeRenameDTO;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SeedTypeService {

    private final SeedTypeDao seedTypeDao;

    public SeedTypeService(SeedTypeDao seedTypeDao) {
        this.seedTypeDao = seedTypeDao;
    }

    public Optional<SeedType> findSeedTypeBydId(String username, Long id) {
        return seedTypeDao.findSeedTypeById(username, id);
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
    public void renameSeedType(String username, SeedTypeRenameDTO seedTypeRenameDTO) throws NotFoundException {
        Optional<SeedType> seedType = seedTypeDao.findSeedTypeById(username, seedTypeRenameDTO.getSeedTypeId());
        if (seedType.isEmpty()) {
            throw new NotFoundException("SeedType을 찾을 수 없습니다. username="+username);
        }
        seedType.get().rename(seedTypeRenameDTO.getNewSeedTypeName());
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
