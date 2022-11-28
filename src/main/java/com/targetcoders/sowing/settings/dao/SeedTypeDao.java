package com.targetcoders.sowing.settings.dao;

import com.targetcoders.sowing.member.dao.MemberDao;
import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.member.domain.SeedType;
import com.targetcoders.sowing.settings.dto.AddSeedTypeDTO;
import com.targetcoders.sowing.settings.exception.SeedTypeDuplicateException;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SeedTypeDao {

    private final MemberDao memberDao;

    public void addSeedType(String username, AddSeedTypeDTO addSeedTypeDTO) throws NotFoundException {
        String seedTypeName = addSeedTypeDTO.getSeedTypeName();
        Member member = memberDao.findByUsername(username);
        List<SeedType> seedTypes = member.getSettings().getSeedTypes();
        if (isDuplicated(seedTypeName, seedTypes)) {
            throw new SeedTypeDuplicateException(seedTypeName + " already exist.");
        }
        seedTypes.add(new SeedType(seedTypeName));
    }

    private boolean isDuplicated(String seedTypeName, List<SeedType> seedTypes) {
        return seedTypes.stream().anyMatch((seedType) -> seedType.getName().equals(seedTypeName));
    }

    public List<SeedType> seedTypes(String username) throws NotFoundException {
        Member member = memberDao.findByUsername(username);
        return member.getSettings().getSeedTypes();
    }

    public void removeSeedType(String username, String seedTypeName) throws NotFoundException {
        Member member = memberDao.findByUsername(username);
        List<SeedType> seedTypes = member.getSettings().getSeedTypes();
        SeedType foundSeedType = seedTypes.stream().filter(seedType -> seedType.getName().equals(seedTypeName)).findFirst().orElse(null);
        seedTypes.remove(foundSeedType);
    }
}