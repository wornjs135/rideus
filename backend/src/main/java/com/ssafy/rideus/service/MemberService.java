package com.ssafy.rideus.service;

import com.ssafy.rideus.common.exception.NotFoundException;
import com.ssafy.rideus.domain.Member;
import com.ssafy.rideus.dto.member.request.MemberUpdateRequest;
import com.ssafy.rideus.repository.jpa.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ssafy.rideus.common.exception.NotFoundException.USER_NOT_FOUND;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    @Transactional
    public void updateMember(MemberUpdateRequest request, Member member) {
        Member findMember = validateMember(member);
        findMember.updateMember(request);
    }

    public Member validateMember(Member member) {
        Member findMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        return findMember;
    }
}
