package com.ssafy.rideus.service;

import com.ssafy.rideus.common.exception.NotFoundException;
import com.ssafy.rideus.domain.Member;
import com.ssafy.rideus.domain.MemberTag;
import com.ssafy.rideus.domain.Record;
import com.ssafy.rideus.domain.collection.MongoRecord;
import com.ssafy.rideus.dto.member.request.MemberMoreInfoReq;
import com.ssafy.rideus.dto.member.response.MemberMeRes;
import com.ssafy.rideus.dto.record.response.MyRideRecordRes;
import com.ssafy.rideus.dto.record.response.RecordTotalResponse;
import com.ssafy.rideus.dto.tag.response.MemberTagResponse;
import com.ssafy.rideus.repository.jpa.MemberRepository;
import com.ssafy.rideus.repository.jpa.MemberTagRepository;
import com.ssafy.rideus.repository.jpa.RecordRepository;
import com.ssafy.rideus.repository.mongo.MongoRecordRepository;
import com.ssafy.rideus.repository.query.MemberQueryRepository;
import com.ssafy.rideus.repository.query.TagQueryRepository;
import lombok.RequiredArgsConstructor;
import com.ssafy.rideus.dto.member.request.MemberUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ssafy.rideus.common.exception.NotFoundException.RECORD_NOT_FOUND;
import static com.ssafy.rideus.common.exception.NotFoundException.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final TagQueryRepository tagQueryRepository;
    private final MongoRecordRepository mongoRecordRepository;

    private final RecordRepository recordRepository;
    private final MemberTagRepository memberTagRepository;

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
        Member member = findById(memberId);
        return new RecordTotalResponse(member.getTotalTime(),member.getTotalDistance());
    }

    @Transactional(readOnly = true)
    public List<MemberTagResponse> getMyTag(Long memberId) {
        List<MemberTag> myTagList = memberTagRepository.findByIdWithTag(memberId);
        return myTagList.stream().map(memberTag -> MemberTagResponse.from(memberTag)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MyRideRecordRes> getRecentRecord(Long memberId) {
        List<MyRideRecordRes> myRideRecordResList = new ArrayList<>();
        recordRepository.findTop5RecordsByMemberIdOrderByIdDesc(memberId).forEach(record -> {
            // 공유된 주행 코스 탔으면
            if (record.getCourse() != null) {
                myRideRecordResList.add(MyRideRecordRes.sharedMyRide(record));
            } else {

                MongoRecord mongoRecord = mongoRecordRepository.findById(record.getId())
                        .orElseThrow(() -> new NotFoundException(RECORD_NOT_FOUND));
                MyRideRecordRes myRideRecordRes = MyRideRecordRes.unSharedMyRide(record);
                myRideRecordRes.setLatLng(mongoRecord);
                // 공유되지 않은 나만의 주행
                myRideRecordResList.add(myRideRecordRes);
            }
        });

        return myRideRecordResList;
    }

    @Transactional(readOnly = true)
    public List<MyRideRecordRes> getMyRideRecord(Long memberId) {

        List<MyRideRecordRes> myRideRecordResList = new ArrayList<>();
        recordRepository.findMyRideRecentRecord(memberId).forEach(record -> {
            MongoRecord mongoRecord = mongoRecordRepository.findById(record.getId())
                    .orElseThrow(() -> new NotFoundException(RECORD_NOT_FOUND));

            // 공유된 나만의 주행
            if (record.getCourse() != null) {
                MyRideRecordRes myRideRecordRes = MyRideRecordRes.sharedMyRide(record);
                myRideRecordRes.computeIsSingle(mongoRecord.getParticipants().size());
                myRideRecordResList.add(myRideRecordRes);

            } else {

                MyRideRecordRes myRideRecordRes = MyRideRecordRes.unSharedMyRide(record);
                myRideRecordRes.setLatLng(mongoRecord);
                myRideRecordRes.computeIsSingle(mongoRecord.getParticipants().size());
                // 공유되지 않은 나만의 주행
                myRideRecordResList.add(myRideRecordRes);
            }
        });

        return myRideRecordResList;
    }
}
