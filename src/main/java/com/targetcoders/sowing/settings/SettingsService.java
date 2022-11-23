package com.targetcoders.sowing.settings;

import com.targetcoders.sowing.member.domain.SeedType;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingsService {

    private final SeedTypeDao seedTypeDao;

    @Transactional
    public List<SeedType> seedTypes(String username) throws NotFoundException {
        return seedTypeDao.seedTypes(username);
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
