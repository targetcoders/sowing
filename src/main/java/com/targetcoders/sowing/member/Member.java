package com.targetcoders.sowing.member;

import com.targetcoders.sowing.seed.Seed;
import com.targetcoders.sowing.seed.SeedGroup;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private LocalDateTime registrationDate;
    private LocalDateTime lastAccessDate;
    @Enumerated(value = EnumType.STRING)
    private MemberRole memberRole;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private List<Seed> seedList;

    public static Member create(String userName, String nickName, String password, LocalDateTime registrationDate, LocalDateTime lastAccessDate) {
        return new Member(null, userName, password, nickName, registrationDate, lastAccessDate, MemberRole.ROLE_USER, new ArrayList<>());
    }

    public void addSeed(Seed seed) {
        this.seedList.add(seed);
    }

    public void update(UpdateMemberDTO updateMemberDTO) {
        this.username = updateMemberDTO.getUsername();
        this.nickname = updateMemberDTO.getNickname();
    }

    public List<SeedGroup> seedGroupList() {
        List<SeedGroup> result = new ArrayList<>();
        Deque<Seed> seedDeque = new ArrayDeque<>(this.seedList);

        while (!seedDeque.isEmpty()) {
            LocalDate groupDate = seedDeque.peekFirst().getSowingDate().toLocalDate();
            SeedGroup seedGroup = SeedGroup.create(groupDate);
            while (!seedDeque.isEmpty()) {
                Seed seed = seedDeque.peekFirst();
                LocalDate seedDate = seed.getSowingDate().toLocalDate();
                if (!groupDate.equals(seedDate)) {
                    break;
                }
                seedGroup.addSeed(seed);
                seedDeque.pollFirst();
            }
            Collections.sort(seedGroup.getSeedList());
            result.add(seedGroup);
        }

        Collections.sort(result);
        return result;
    }

}
