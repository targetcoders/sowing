package com.targetcoders.sowing.member.domain;

import com.targetcoders.sowing.member.dto.UpdateMemberDTO;
import com.targetcoders.sowing.settings.domain.Settings;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "google_jwt_id", nullable = false)
    private GoogleJwt googleJwt;
    @Column(name = "nickname", nullable = false)
    private String nickname;
    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;
    @Column(name = "last_access_date", nullable = false)
    private LocalDate lastAccessDate;
    @Column(name = "member_role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberRole memberRole;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "settings_id", nullable = false)
    private Settings settings;

    public static Member create(String userName, String nickName, GoogleJwt googleJwt, LocalDate registrationDate, LocalDate lastAccessDate, Settings settings) {
        return new Member(null, userName, googleJwt, nickName, registrationDate, lastAccessDate, MemberRole.ROLE_USER, settings);
    }

    public void update(UpdateMemberDTO updateMemberDTO) {
        this.username = updateMemberDTO.getUsername();
        this.nickname = updateMemberDTO.getNickname();
    }

}
