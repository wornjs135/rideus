package com.ssafy.rideus.service;

import com.ssafy.rideus.common.exception.NotMatchException;
import com.ssafy.rideus.domain.Course;
import com.ssafy.rideus.domain.Record;
import com.ssafy.rideus.domain.collection.MongoRecord;
import com.ssafy.rideus.dto.record.request.FinishRiddingRequest;
import com.ssafy.rideus.dto.record.request.SaveCoordinatesRequest;
import com.ssafy.rideus.dto.record.response.CreateRecordResponse;
import com.ssafy.rideus.dto.record.type.RiddingType;
import com.ssafy.rideus.dto.rideroom.common.ParticipantDto;
import com.ssafy.rideus.dto.rideroom.request.GroupRiddingRequest;
import com.ssafy.rideus.dto.rideroom.response.CreateRideRoomResponse;
import com.ssafy.rideus.dto.rideroom.response.GroupRiddingResponse;
import com.ssafy.rideus.common.exception.DuplicateException;
import com.ssafy.rideus.common.exception.NotFoundException;
import com.ssafy.rideus.domain.Member;
import com.ssafy.rideus.domain.RideRoom;
import com.ssafy.rideus.domain.redish.RedisRideRoom;
import com.ssafy.rideus.dto.rideroom.response.RedisRideRoomResponse;
import com.ssafy.rideus.repository.jpa.CourseRepository;
import com.ssafy.rideus.repository.jpa.MemberRepository;
import com.ssafy.rideus.repository.jpa.RecordRepository;
import com.ssafy.rideus.repository.jpa.RideRoomRepository;
import com.ssafy.rideus.repository.mongo.MongoRecordRepository;
import com.ssafy.rideus.repository.redis.RedisRideRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.ssafy.rideus.common.exception.DuplicateException.GROUP_PARTICIPATE_DUPLICATE;
import static com.ssafy.rideus.common.exception.NotFoundException.*;
import static com.ssafy.rideus.common.exception.NotMatchException.MEMBER_RECORD_NOT_MATCH;
import static com.ssafy.rideus.dto.record.type.RiddingType.group;
import static com.ssafy.rideus.dto.record.type.RiddingType.single;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RideService {

    private final MemberRepository memberRepository;
    private final RideRoomRepository rideRoomRepository;
    private final RedisRideRoomRepository redisRideRoomRepository;
    private final MongoRecordRepository mongoRecordRepository;
    private final CourseRepository courseRepository;
    private final RecordRepository recordRepository;

    @Transactional
    public CreateRideRoomResponse createRiddingRoom(Member member) {
        Member findMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        return CreateRideRoomResponse.from(rideRoomRepository.save(RideRoom.create(findMember)).getId());
    }

    public GroupRiddingResponse searchMemberInfo(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        GroupRiddingResponse groupRiddingResponse = new GroupRiddingResponse();
        groupRiddingResponse.setMemberId(findMember.getId());
        groupRiddingResponse.setNickname(findMember.getNickname());

        return groupRiddingResponse;
    }

    @Transactional
    public RedisRideRoomResponse enterRiddingRoom(Long memberId, GroupRiddingRequest request) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        Optional<RedisRideRoom> rideRoom = redisRideRoomRepository.findById(request.getRideRoomId());

        RedisRideRoom result = null;
        if (rideRoom.isPresent()) {
            RedisRideRoom realRideRoom = rideRoom.get();
            List<ParticipantDto> participants = realRideRoom.getParticipants();

            for (ParticipantDto participant : participants) {
                if (participant.getMemberId() == findMember.getId()) {
                    throw new DuplicateException(GROUP_PARTICIPATE_DUPLICATE);
                }
            }

            participants.add(ParticipantDto.from(findMember));
            result = redisRideRoomRepository.save(realRideRoom);
        } else {
            RedisRideRoom redisRideRoom = RedisRideRoom.create(request.getRideRoomId());
            redisRideRoom.getParticipants().add(ParticipantDto.from(findMember));
            result = redisRideRoomRepository.save(redisRideRoom);
        }

        return RedisRideRoomResponse.from(result);
    }

    @Transactional
    public CreateRecordResponse startGroupRidding(Member member) {
        MongoRecord saveRecord = mongoRecordRepository.save(MongoRecord.create(member.getId()));

        return CreateRecordResponse.from(saveRecord.getId());
    }

    @Transactional
    public void finishRidding(Member member, RiddingType riddingType, FinishRiddingRequest request) {
        Course findCourse = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new NotFoundException(COURSE_NOT_FOUND));
        Member findMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        Record createdRecord = Record.from(findMember, findCourse, request);
        recordRepository.save(createdRecord); // mysql에 최종 기록 저장

        // 개인 기록 update
        findMember.updateRecord(request.getDistance(), request.getTime());

        MongoRecord mongoRecord = mongoRecordRepository.findById(request.getRecordId())
                .orElseThrow(() -> new NotFoundException(RECORD_NOT_FOUND));

        if (riddingType.equals(single)) {
            mongoRecord.getParticipants().add(ParticipantDto.from(findMember)); // 같이 탄 사람에 자신 혼자.
        } else if (riddingType.equals(group)) {
            RedisRideRoom findRideRoom = redisRideRoomRepository.findById(request.getRideRoomId())
                    .orElseThrow(() -> new NotFoundException(RIDEROOM_NOT_FOUND));
            mongoRecord.updateParticipants(findRideRoom.getParticipants());
        }
        mongoRecordRepository.save(mongoRecord);
    }

    @Transactional
    public void saveCoordinatesPerPeriod(Member member, String recordId, SaveCoordinatesRequest saveCoordinatesRequest) {
        MongoRecord mongoRecord = mongoRecordRepository.findById(recordId)
                .orElseThrow(() -> new NotFoundException(RECORD_NOT_FOUND));
        if (mongoRecord.getMemberId() != member.getId()) {
            throw new NotMatchException(MEMBER_RECORD_NOT_MATCH);
        }

        mongoRecord.getCoordinates().addAll(saveCoordinatesRequest.getCoordinates());
        mongoRecordRepository.save(mongoRecord);
    }
}
