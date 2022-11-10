package com.targetcoders.sowing.member.repository;

import com.targetcoders.sowing.member.domain.Member;
import com.targetcoders.sowing.member.dto.UpdateMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member findById(Long memberId) {
        return em.find(Member.class, memberId);
    }

    public void remove(Member member) {
        em.remove(member);
    }

    public void updateMember(UpdateMemberDTO updateMemberDTO) {
        Member member = em.find(Member.class, updateMemberDTO.getId());
        member.update(updateMemberDTO);
    }

    public List<Member> findByUsername(String memberUsername) {
        return em.createQuery("select m from Member m where m.username = :userName")
                .setParameter("userName", memberUsername)
                .getResultList();
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m")
                .getResultList();
    }

}
