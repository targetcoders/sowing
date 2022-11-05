package com.targetcoders.sowing.seed.domain;

import com.targetcoders.sowing.member.Member;
import com.targetcoders.sowing.seed.dto.UpdateSeedDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime sowingDate;

    public static Seed create(SeedType type, Member member, String title, String content, LocalDateTime doneDate) {
        return new Seed(null, type, member, title, content, doneDate);
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