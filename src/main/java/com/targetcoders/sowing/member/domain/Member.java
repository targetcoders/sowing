package com.targetcoders.sowing.member.domain;

import com.targetcoders.sowing.member.dto.UpdateMemberDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "google_tokens_id")
    private GoogleTokens googleTokens;
    private String sowingRefreshToken;
    private String nickname;
    private LocalDate registrationDate;
    private LocalDate lastAccessDate;
    @Enumerated(value = EnumType.STRING)
    private MemberRole memberRole;


    public static Member create(String userName, String nickName, GoogleTokens googleTokens, String sowingRefreshToken, LocalDate registrationDate, LocalDate lastAccessDate) {
        return new Member(null, userName, googleTokens, sowingRefreshToken, nickName, registrationDate, lastAccessDate, MemberRole.ROLE_USER);
    }

    public void update(UpdateMemberDTO updateMemberDTO) {
        this.username = updateMemberDTO.getUsername();
        this.nickname = updateMemberDTO.getNickname();
    }

}
