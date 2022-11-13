package com.targetcoders.sowing.seed.domain;

import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.seed.dto.UpdateSeedDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Seed implements Comparable<Seed> {

    @Id
    @GeneratedValue
    @Column(name = "seed_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "seed_type")
    private SeedType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private String title;
    private String content;
    private LocalDate sowingDate;

    public static Seed create(SeedType type, Member member, String title, String content, LocalDate sowingDate) {
        return new Seed(null, type, member, title, content, sowingDate);
    }

    public void update(UpdateSeedDTO updateSeedDto) {
        type = updateSeedDto.getType();
        title = updateSeedDto.getTitle();
        content = updateSeedDto.getContent();
        sowingDate = updateSeedDto.getSowingDate();
    }

    @Override
    public int compareTo(Seed o) {
        return type.compareTo(o.type);
    }
}