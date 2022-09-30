package com.targetcoders.sowing.member;

import com.targetcoders.sowing.seed.Seed;
import com.targetcoders.sowing.seed.SeedService;
import com.targetcoders.sowing.seed.SeedType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberTest {

    @Autowired SeedService seedService;
    @Autowired MemberService memberService;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("멤버를 조회하면 해당 멤버가 등록한 Seed 들도 모두 조회된다.")
    @Transactional
    void saveAndFind() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Member member = Member.create("greenneuron", "nickname", now, now);
        Long saveMemberId = memberService.saveMember(member);

        //when
        Seed seed1 = Seed.create(SeedType.STUDY, member, "제목1", "내용1", now);
        seedService.saveSeed(seed1);
        Seed seed2 = Seed.create(SeedType.STUDY, member, "제목2", "내용2", now);
        seedService.saveSeed(seed2);
        Seed seed3 = Seed.create(SeedType.STUDY, member, "제목3", "내용3", now);
        seedService.saveSeed(seed3);

        Member findMember2 = memberService.findById(saveMemberId);

        em.flush();

        //then
        Assertions.assertThat(findMember2.getSeedList().size()).isEqualTo(3);
    }

}