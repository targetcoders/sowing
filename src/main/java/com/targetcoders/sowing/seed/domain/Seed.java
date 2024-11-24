package com.targetcoders.sowing.seed.domain;

import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.seedtype.domain.SeedType;
import com.targetcoders.sowing.seed.dto.SeedUpdateDTO;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seed_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "seed_type_id", nullable = false)
    private SeedType seedType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    private String title;
    private String content;
    private LocalDate sowingDate;

    public static Seed create(SeedType seedType, Member member, String title, String content, LocalDate sowingDate) {
        return new Seed(null, seedType, member, title, content, sowingDate);
    }

    public void update(SeedUpdateDTO seedUpdateDto) {
        seedType = seedUpdateDto.getSeedType();
        title = seedUpdateDto.getTitle();
        content = seedUpdateDto.getContent();
        sowingDate = seedUpdateDto.getSowingDate();
    }

    @Override
    public int compareTo(Seed o) {
        return seedType.getName().compareTo(o.seedType.getName());
    }
}