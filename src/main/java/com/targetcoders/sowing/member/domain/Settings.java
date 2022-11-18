package com.targetcoders.sowing.member.domain;

import com.targetcoders.sowing.seed.domain.DefaultSeedType;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Settings {

    @Id
    @Column(name = "settings_id")
    @GeneratedValue
    private Long id;

    @JoinColumn(name = "settings_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<SeedType> seedTypes;

    public static Settings create() {
        return new Settings();
    }

    protected Settings() {
        List<SeedType> seedTypes = new ArrayList<>();
        for (DefaultSeedType defaultSeedType : DefaultSeedType.values()) {
            seedTypes.add(new SeedType(defaultSeedType.name()));
        }
        this.seedTypes = seedTypes;
    }

}
