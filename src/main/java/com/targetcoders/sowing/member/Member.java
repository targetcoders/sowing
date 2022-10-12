package com.targetcoders.sowing.member;

import com.targetcoders.sowing.seed.Seed;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

}
