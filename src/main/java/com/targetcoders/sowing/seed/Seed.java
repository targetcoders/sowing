package com.targetcoders.sowing.seed;

import com.targetcoders.sowing.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Seed {

    @Id
    @GeneratedValue
    @Column(name = "seed_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "seed_type")
    private SeedType type;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;
    private String title;
    private String content;
    private LocalDateTime sowingDate;

    public static Seed create(SeedType type, Member member, String title, String content, LocalDateTime doneDate) {
        Seed seed = new Seed(null, type, member, title, content, doneDate);
        member.addSeed(seed);
        return seed;
    }

    public void update(UpdateSeedDTO updateSeedDto) {
        type = updateSeedDto.getType();
        title = updateSeedDto.getTitle();
        content = updateSeedDto.getContent();
        sowingDate = updateSeedDto.getSowingDate();
    }

}