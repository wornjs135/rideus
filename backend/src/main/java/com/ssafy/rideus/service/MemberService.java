package com.ssafy.rideus.service;

import com.ssafy.rideus.common.exception.NotFoundException;
import com.ssafy.rideus.domain.Member;
import com.ssafy.rideus.domain.Record;
import com.ssafy.rideus.dto.member.request.MemberMoreInfoReq;
import com.ssafy.rideus.dto.member.response.MemberMeRes;
import com.ssafy.rideus.dto.record.response.MyRideRecordRes;
import com.ssafy.rideus.dto.record.response.RecordTotalResponse;
import com.ssafy.rideus.dto.tag.response.MemberTagResponse;
import com.ssafy.rideus.repository.jpa.MemberRepository;
import com.ssafy.rideus.repository.jpa.RecordRepository;
import com.ssafy.rideus.repository.query.MemberQueryRepository;
import com.ssafy.rideus.repository.query.TagQueryRepository;
import lombok.RequiredArgsConstructor;
import com.ssafy.rideus.dto.member.request.MemberUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.ssafy.rideus.common.exception.NotFoundException.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private  final TagQueryRepository tagQueryRepository;

    private final RecordRepository recordRepository;
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

    @Transactional(readOnly = true)
    public MemberMeRes findByLoginMember(Long memberId) {
        Member findMember = findById(memberId);
        return MemberMeRes.of(findMember);
    }

    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }
    @Transactional
    public void updateMember(MemberUpdateRequest request, Long memberId) {
        Member findMember = validateMember(memberId);
        findMember.updateMember(request);
    }

    @Transactional(readOnly = true)
    public Member validateMember(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        return findMember;
    }
    @Transactional(readOnly = true)
    public RecordTotalResponse getTotalRecord(Long memberId) {
        return memberQueryRepository.searchTotalRecord(memberId);
    }

    @Transactional(readOnly = true)
    public List<MemberTagResponse> getMyTag(Long memberId) {
        return tagQueryRepository.searchMemberTag(memberId);
    }

    @Transactional(readOnly = true)
    public List<MyRideRecordRes> getRecentRecord(Long memberId) {
        List<MyRideRecordRes> myRideRecordResList = new ArrayList<>();
        recordRepository.findTop5RecordsByMemberIdOrderByIdDesc(memberId).forEach(record -> {
            // 공유된 주행 코스 탔으면
            if (record.getCourse() != null) {
                myRideRecordResList.add(MyRideRecordRes.sharedMyRide(record));
            } else {
                // 공유되지 않은 나만의 주행
                myRideRecordResList.add(MyRideRecordRes.unSharedMyRide(record));
            }
        });

        return myRideRecordResList;
    }

    @Transactional(readOnly = true)
    public List<MyRideRecordRes> getMyRideRecord(Long memberId) {

        List<MyRideRecordRes> myRideRecordResList = new ArrayList<>();
        recordRepository.findMyRideRecentRecord(memberId).forEach(record -> {
            // 공유된 나만의 주행
            if (record.getCourse() != null) {
                myRideRecordResList.add(MyRideRecordRes.sharedMyRide(record));
            } else {
            // 공유되지 않은 나만의 주행
                myRideRecordResList.add(MyRideRecordRes.unSharedMyRide(record));
            }
        });

        return myRideRecordResList;
    }
}
