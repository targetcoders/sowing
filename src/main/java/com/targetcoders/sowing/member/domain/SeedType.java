package com.targetcoders.sowing.member.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter @NoArgsConstructor
public class SeedType {

    @Id
    @GeneratedValue
    @Column(name = "seedtype_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private String name;

    public SeedType(Member member, String name) {
        this.member = member;
        this.name = name;
    }

    public static SeedType create(Member member, String name) {
        return new SeedType(member, name);
    }

}
