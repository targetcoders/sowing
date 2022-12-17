package com.targetcoders.sowing.seedtype.dao;

import com.targetcoders.sowing.member.dao.MemberDao;
import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.seed.repository.SeedRepository;
import com.targetcoders.sowing.seedtype.domain.SeedType;
import com.targetcoders.sowing.seedtype.dto.AddSeedTypeDTO;
import com.targetcoders.sowing.seedtype.exception.SeedTypeDuplicateException;
import com.targetcoders.sowing.seedtype.repository.SeedTypeRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SeedTypeDao {

    private final MemberDao memberDao;
    private final SeedTypeRepository seedTypeRepository;
    private final SeedRepository seedRepository;

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

    public Optional<SeedType> findSeedTypeById(String username, Long id) {
        return seedTypeRepository.findSeedTypeById(username, id);
    }

    public boolean hasSeedType(String username, String seedTypeName) {
        return seedRepository.findByUsername(username)
                .stream()
                .anyMatch(seed -> seed.getSeedType().getName().equals(seedTypeName));
    }
}
