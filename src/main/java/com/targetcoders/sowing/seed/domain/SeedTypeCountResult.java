package com.targetcoders.sowing.seed.domain;

import com.targetcoders.sowing.seedtype.domain.SeedType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
public class SeedTypeCountResult {
    private final HashMap<String, Long> typeCountMap = new HashMap<>();

    public SeedTypeCountResult(List<SeedType> seedTypes, List<Seed> seeds) {
        for(SeedType seedType : seedTypes) {
            typeCountMap.put(seedType.getName(), 0L);
        }
        count(seeds);
    }

    private void count(List<Seed> seeds) {
        for (Seed seed : seeds) {
            for (String seedTypeName : typeCountMap.keySet()) {
                if (seed.getSeedType().getName().equals(seedTypeName)) {
                    Long nextCount = typeCountMap.get(seedTypeName) + 1;
                    typeCountMap.put(seedTypeName, nextCount);
                }
            }
        }
    }

    public long total() {
        long result = 0;
        for (Long count : typeCountMap.values()) {
            result += count;
        }
        return result;
    }

    public Set<String> seedTypeNameSet() {
        return typeCountMap.keySet();
    }

    public Long getCount(String key) {
        Long result = typeCountMap.get(key);
        if (result == null) {
            return 0L;
        }
        return result;
    }
}
