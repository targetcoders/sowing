package com.targetcoders.sowing.seed.dao;

import com.targetcoders.sowing.authentication.domain.JwtToken;
import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.member.domain.SeedType;
import com.targetcoders.sowing.member.dto.CreateMemberDTO;
import com.targetcoders.sowing.member.service.MemberService;
import com.targetcoders.sowing.seed.domain.DefaultSeedType;
import com.targetcoders.sowing.seed.domain.Seed;
import com.targetcoders.sowing.settings.dao.SeedTypeDao;
import com.targetcoders.sowing.settings.repository.SeedTypeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SeedDaoTest {

    @Autowired MemberService memberService;
    @Autowired SeedDao seedDao;
    @Autowired SeedTypeRepository seedTypeRepository;
    @Autowired EntityManager em;

    @Test
    @DisplayName("멤버를 조회하면 해당 멤버의 모든 시드도 조회된다")
    @Transactional
    void saveAndFind() {
        //given
        LocalDate now = LocalDate.now();
        CreateMemberDTO createMemberDTO = new CreateMemberDTO("greenneuron@naver.com", "nickname", "accessToken", "refreshToken", invalidJwtToken());
        Member saveMember = memberService.saveMember(createMemberDTO);
        SeedType seedType = new SeedType(DefaultSeedType.STUDY.toString());
        seedTypeRepository.saveSeedType(seedType);


        //when
        Seed seed1 = Seed.create(seedType, saveMember, "제목1", "내용1", now);
        seedDao.saveSeed(seed1);
        Seed seed2 = Seed.create(seedType, saveMember, "제목2", "내용2", now);
        seedDao.saveSeed(seed2);
        Seed seed3 = Seed.create(seedType, saveMember, "제목3", "내용3", now);
        seedDao.saveSeed(seed3);
        em.flush();

        //then
        List<Seed> seeds = seedDao.findYearSeeds(now.getYear(), "greenneuron@naver.com");
        assertThat(seeds.size()).isEqualTo(3);
    }

    private JwtToken invalidJwtToken() {
        return new JwtToken("a.b.c");
    }
}
