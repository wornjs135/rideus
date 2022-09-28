package com.ssafy.rideus.service;

import com.ssafy.rideus.common.exception.NotMatchException;
import com.ssafy.rideus.domain.Course;
import com.ssafy.rideus.domain.Record;
import com.ssafy.rideus.domain.base.Coordinate;
import com.ssafy.rideus.domain.collection.CourseCoordinate;
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
import com.ssafy.rideus.repository.mongo.CourseCoordinateRepository;
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
    private final CourseCoordinateRepository courseCoordinateRepository;

    @Transactional
    public CreateRideRoomResponse createRiddingRoom(Long memberId, String courseId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        Course findCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException(COURSE_NOT_FOUND));

        return CreateRideRoomResponse.from(rideRoomRepository.save(RideRoom.create(findMember)).getId(), findCourse.getId(), findMember.getNickname());
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
                if (participant.getMemberId().equals(findMember.getId())) {
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
    public CreateRecordResponse startRidding(Long memberId) {
        MongoRecord saveRecord = mongoRecordRepository.save(MongoRecord.create(memberId));

        return CreateRecordResponse.from(saveRecord.getId());
    }

    @Transactional
    public CreateRecordResponse finishRidding(Long memberId, RiddingType riddingType, FinishRiddingRequest request) {
        MongoRecord mongoRecord = mongoRecordRepository.findById(request.getRecordId())
                .orElseThrow(() -> new NotFoundException(RECORD_NOT_FOUND));

        Course findCourse = null;
        CourseCoordinate mongoCourse = null;
        List<Coordinate> recordCoordinates = null;
        List<Coordinate> courseCoordinates = null;

        // 나만의 주행이면 course 정보들이 null, 기본 코스 주행이면 course 정보들 찾아와서 넣어줌
        if (request.getCourseId() != null) {
            findCourse = courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new NotFoundException(COURSE_NOT_FOUND));
            mongoCourse = courseCoordinateRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new NotFoundException(COURSE_NOT_FOUND));
            recordCoordinates = mongoRecord.getCoordinates();
            courseCoordinates = mongoCourse.getCoordinates();
        }
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        // 개인 기록 update
        findMember.updateRecord(request.getDistance(), request.getTime());

        RideRoom rideRoom = null;
        if (riddingType.equals(single)) {
            rideRoom = rideRoomRepository.save(RideRoom.create(findMember)); // 싱글 주행일때는 그룹방 새로 생성해서 넣어주기
            mongoRecord.getParticipants().add(ParticipantDto.from(findMember)); // 같이 탄 사람에 자신 혼자.
        } else if (riddingType.equals(group)) {
            RedisRideRoom findRideRoom = redisRideRoomRepository.findById(request.getRideRoomId())
                    .orElseThrow(() -> new NotFoundException(RIDEROOM_NOT_FOUND)); // 레디스에서 참가자 리스트 불러오기
            mongoRecord.updateParticipants(findRideRoom.getParticipants()); // 참가자들 추가

            rideRoom = rideRoomRepository.findById(findRideRoom.getId())
                    .orElseThrow(() -> new NotFoundException(RIDEROOM_NOT_FOUND));
        }
        mongoRecordRepository.save(mongoRecord);

        // mysql에 최종 기록 저장
        Record saveRecord = recordRepository.save(Record.from(findMember, findCourse, request,
                recordCoordinates, courseCoordinates, rideRoom));

        return CreateRecordResponse.from(saveRecord.getId());
    }

    @Transactional
    public void saveCoordinatesPerPeriod(Long memberId, String recordId, SaveCoordinatesRequest saveCoordinatesRequest) {
        MongoRecord mongoRecord = mongoRecordRepository.findById(recordId)
                .orElseThrow(() -> new NotFoundException(RECORD_NOT_FOUND));
        if (!mongoRecord.getMemberId().equals(memberId)) {
            throw new NotMatchException(MEMBER_RECORD_NOT_MATCH);
        }

        mongoRecord.getCoordinates().addAll(saveCoordinatesRequest.getCoordinates());
        mongoRecordRepository.save(mongoRecord);
    }
}
