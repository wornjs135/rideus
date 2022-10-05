package com.ssafy.rideus.repository.jpa;

import com.ssafy.rideus.domain.MemberTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberTagRepository extends JpaRepository<MemberTag, Long> {

    List<MemberTag> findDistinctByMemberId(Long memberId);

    @Query("select mt from MemberTag mt join fetch mt.tag where mt.member.id = :memberId")
    List<MemberTag> findByIdWithTag(Long memberId);
}
