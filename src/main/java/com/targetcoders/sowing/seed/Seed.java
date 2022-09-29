package com.targetcoders.sowing.seed;

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

    private Long userId;
    private String title;
    private String content;
    private LocalDateTime sowingDate;

    public static Seed create(SeedType type, Long userId, String title, String content, LocalDateTime doneDate) {
        return new Seed(null, type, userId, title, content, doneDate);
    }

    public void update(UpdateSeedDTO updateSeedDto) {
        type = updateSeedDto.getType();
        title = updateSeedDto.getTitle();
        content = updateSeedDto.getContent();
        sowingDate = updateSeedDto.getSowingDate();
    }

}