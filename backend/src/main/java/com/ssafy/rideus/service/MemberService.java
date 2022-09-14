package com.ssafy.rideus.service;

import com.ssafy.rideus.common.exception.NotFoundException;
import com.ssafy.rideus.domain.Member;
import com.ssafy.rideus.dto.member.request.MemberMoreInfoReq;
import com.ssafy.rideus.dto.member.response.MemberMeRes;
import com.ssafy.rideus.repository.jpa.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ssafy.rideus.common.exception.NotFoundException.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public void updateMoreInformation(MemberMoreInfoReq memberMoreInfoReq, long memberId) {
        Member findMember = findById(memberId);
        findMember.updateMoreInfo(memberMoreInfoReq);
        memberRepository.save(findMember);
    }

    @Transactional(readOnly = true)
    public boolean checkNicknameDuplicate(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    @Transactional(readOnly = true)
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    public MemberMeRes findByLoginMember(Long memberId) {
        Member findMember = findById(memberId);
        return MemberMeRes.of(findMember);
    }

}
