package com.targetcoders.sowing.member.domain;

import com.targetcoders.sowing.seed.domain.DefaultSeedType;
import lombok.Getter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
public class Settings {

    @Id
    @Column(name = "settings_id")
    @GeneratedValue
    private Long id;

    @ElementCollection
    @CollectionTable(name = "seedtype", joinColumns = @JoinColumn(name = "seedtype_id"))
    @Column(name = "seedtype")
    private final Set<String> seedTypes;

    public static Settings create() {
        return new Settings();
    }

    protected Settings() {
        HashSet<String> seedTypes = new HashSet<>();
        for (DefaultSeedType defaultSeedType : DefaultSeedType.values()) {
            seedTypes.add(defaultSeedType.name());
        }
        this.seedTypes = seedTypes;
    }

}
