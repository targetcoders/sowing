package com.targetcoders.sowing.member.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class SeedType {

    @Id
    @GeneratedValue
    @Column(name = "seed_type_id")
    private Long id;
    private String name;

    public SeedType(String name) {
        this.name = name;
    }
}
