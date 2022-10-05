package com.ssafy.rideus.service;

import com.ssafy.rideus.common.exception.MaxRoomException;
import com.ssafy.rideus.common.exception.NotMatchException;
import com.ssafy.rideus.domain.Course;
import com.ssafy.rideus.domain.Record;
import com.ssafy.rideus.domain.base.Coordinate;
import com.ssafy.rideus.domain.collection.CourseCoordinate;
import com.ssafy.rideus.domain.collection.MongoRecord;
import com.ssafy.rideus.domain.type.Color;
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
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.ssafy.rideus.common.exception.DuplicateException.GROUP_PARTICIPATE_DUPLICATE;
import static com.ssafy.rideus.common.exception.MaxRoomException.*;
import static com.ssafy.rideus.common.exception.NotFoundException.*;
import static com.ssafy.rideus.common.exception.NotMatchException.MEMBER_RECORD_NOT_MATCH;
import static com.ssafy.rideus.dto.record.type.RiddingType.group;
import static com.ssafy.rideus.dto.record.type.RiddingType.single;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RideService {

    private final MemberRepository memberRepository;
    private final RideRoomRepository rideRoomRepository;
    private final RedisRideRoomRepository redisRideRoomRepository;
    private final MongoRecordRepository mongoRecordRepository;
    private final CourseRepository courseRepository;
    private final RecordRepository recordRepository;
    private final CourseCoordinateRepository courseCoordinateRepository;
    private final MongoTemplate mongoTemplate;

    private final int MAX_PLAYER_COUNT = 6;

    @Transactional
    public CreateRideRoomResponse createRiddingRoom(Long memberId, String courseId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        Course findCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException(COURSE_NOT_FOUND));

        return CreateRideRoomResponse.from(rideRoomRepository.save(RideRoom.create(findMember)).getId(), findCourse.getId(), findMember.getNickname());
    }

    public GroupRiddingResponse searchMemberInfo(Long memberId, GroupRiddingRequest request) {
        Optional<RedisRideRoom> rideRoom = redisRideRoomRepository.findById(request.getRideRoomId());
        GroupRiddingResponse groupRiddingResponse = new GroupRiddingResponse();

        if (rideRoom.isPresent()) {
            RedisRideRoom redisRideRoom = rideRoom.get();
            for (ParticipantDto member : redisRideRoom.getParticipants()) {
                if (member.getMemberId().equals(memberId)) {
                    groupRiddingResponse.setMemberId(member.getMemberId());
                    groupRiddingResponse.setNickname(member.getNickname());
                    groupRiddingResponse.setProfileImageUrl(member.getProfileImageUrl());

                    return groupRiddingResponse;
                }
            }
        }

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
            if (participants.size() > MAX_PLAYER_COUNT) {
                throw new MaxRoomException(GROUP_MAX_PARTICIPANTS);
            }

            for (ParticipantDto participant : participants) {
                if (participant.getMemberId().equals(findMember.getId())) {
                    throw new DuplicateException(GROUP_PARTICIPATE_DUPLICATE);
                }
            }
            ParticipantDto participant = ParticipantDto.from(findMember);
            participant.setColor(getNewColor(realRideRoom).name());
            participants.add(participant);

            result = redisRideRoomRepository.save(realRideRoom);
        } else {
            RedisRideRoom redisRideRoom = RedisRideRoom.create(request.getRideRoomId());

            ParticipantDto participant = ParticipantDto.from(findMember);
            participant.setColor(getNewColor(redisRideRoom).name());

            redisRideRoom.getParticipants().add(participant);

            result = redisRideRoomRepository.save(redisRideRoom);
        }

        return RedisRideRoomResponse.from(result);
    }

    private Color getNewColor(RedisRideRoom redisRideRoom) {
        Set<Color> usedColors = new HashSet<>();
        for (ParticipantDto participant : redisRideRoom.getParticipants()) {
            usedColors.add(Color.valueOf(participant.getColor()));
        }

        for (int i = 0; i < MAX_PLAYER_COUNT; i++) {
            Color newColor = Color.randomColor();
            if (!usedColors.contains(newColor)) {
                return newColor;
            }
        }
        return Color.RED;
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
//            mongoRecord.getParticipants().add(ParticipantDto.from(findMember)); // 같이 탄 사람에 자신 혼자.
        } else if (riddingType.equals(group)) {
//            RedisRideRoom findRideRoom = redisRideRoomRepository.findById(request.getRideRoomId())
//                    .orElseThrow(() -> new NotFoundException(RIDEROOM_NOT_FOUND)); // 레디스에서 참가자 리스트 불러오기
//            mongoRecord.updateParticipants(findRideRoom.getParticipants()); // 참가자들 추가

            rideRoom = rideRoomRepository.findById(request.getRideRoomId())
                    .orElseThrow(() -> new NotFoundException(RIDEROOM_NOT_FOUND));
        }
//        mongoRecordRepository.save(mongoRecord);

        // mysql에 최종 기록 저장
        Record saveRecord = recordRepository.save(Record.from(findMember, findCourse, request,
                recordCoordinates, courseCoordinates, rideRoom));

        return CreateRecordResponse.from(saveRecord.getId());
    }

    @Transactional
    public CreateRecordResponse saveCoordinatesPerPeriod(Long memberId, SaveCoordinatesRequest saveCoordinatesRequest) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
//        MongoRecord mongoRecord = mongoRecordRepository.findById(recordId)
//                .orElseThrow(() -> new NotFoundException(RECORD_NOT_FOUND));
//        if (!mongoRecord.getMemberId().equals(memberId)) {
//            throw new NotMatchException(MEMBER_RECORD_NOT_MATCH);
//        }

//        log.info("주행 좌표 리스트 DB에서 가져온거: " + mongoRecord.getCoordinates());
//        log.info("주행 좌표 리스트 DB에서 가져온거: " + mongoRecord.getCoordinates().size());

//        Query query = new Query().addCriteria(Criteria.where("_id").is(recordId));
//        Update update = new Update();
//        update.set("coordinates", saveCoordinatesRequest.getCoordinates());
//        update.push("coordinates").each(saveCoordinatesRequest.getCoordinates());
//        log.info("주행 좌표 리스트에 추가: " + mongoRecord.getCoordinates());
//        mongoTemplate.updateFirst(query, update, "record");

//        MongoRecord save = mongoRecordRepository.save(mongoRecord);
//        log.info("몽고 DB에 들어간 거: " + save);

        List<ParticipantDto> participantDtos = new ArrayList<>();
        if (saveCoordinatesRequest.getRideRoomId() == null) { // 싱글주행일때
            participantDtos.add(ParticipantDto.from(findMember));
        } else { // 그룹주행일때
            RedisRideRoom findRideRoom = redisRideRoomRepository.findById(saveCoordinatesRequest.getRideRoomId())
                    .orElseThrow(() -> new NotFoundException(RIDEROOM_NOT_FOUND)); // 레디스에서 참가자 리스트 불러오기
            participantDtos.addAll(findRideRoom.getParticipants());
        }

        MongoRecord insertRecord = mongoRecordRepository.insert(MongoRecord.create(memberId, saveCoordinatesRequest.getCoordinates(), participantDtos));
        return CreateRecordResponse.from(insertRecord.getId());
    }

    public List<ParticipantDto> getRoomParticipants(Long rideRoomId) {
        RedisRideRoom redisRideRoom = redisRideRoomRepository.findById(rideRoomId)
                .orElseThrow(() -> new NotFoundException(RIDEROOM_NOT_FOUND));

        return redisRideRoom.getParticipants();
    }
}
