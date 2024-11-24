package com.targetcoders.sowing.seedtype.domain;

import javax.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter @ToString
@NoArgsConstructor
public class SeedType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seed_type_id")
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;

    public SeedType(String name) {
        this.name = name;
    }

    public void rename(String newName) {
        this.name = newName;
    }

}
