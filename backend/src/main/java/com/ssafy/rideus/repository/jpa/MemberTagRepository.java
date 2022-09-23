package com.ssafy.rideus.repository.jpa;

import com.ssafy.rideus.domain.MemberTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberTagRepository extends JpaRepository<MemberTag, Long> {

    List<MemberTag> findDistinctByMemberId(Long memberId);
}
