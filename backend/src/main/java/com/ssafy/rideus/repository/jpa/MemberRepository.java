package com.ssafy.rideus.repository.jpa;

import com.ssafy.rideus.repository.jpa.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
