package com.targetcoders.sowing.seed.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
public class TypeCounter {
    private final HashMap<String, Long> typeCountMap = new HashMap<>();

    public TypeCounter(Set<String> seedTypes) {
        for(String seedType : seedTypes) {
            typeCountMap.put(seedType, 0L);
        }
    }

    public void count(List<Seed> seeds) {
        for (Seed seed : seeds) {
            for (String seedType : typeCountMap.keySet()) {
                if (seed.getType().equals(seedType)) {
                    Long nextCount = typeCountMap.get(seedType) + 1;
                    typeCountMap.put(seedType, nextCount);
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

    public Set<String> keySet() {
        return typeCountMap.keySet();
    }

    public Long getCount(String key) {
        return typeCountMap.get(key);
    }
}
