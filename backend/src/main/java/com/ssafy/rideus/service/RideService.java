package com.ssafy.rideus.service;

import com.ssafy.rideus.domain.collection.MongoRecord;
import com.ssafy.rideus.dto.record.request.FinishRiddingRequest;
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
import com.ssafy.rideus.repository.jpa.MemberRepository;
import com.ssafy.rideus.repository.jpa.RideRoomRepository;
import com.ssafy.rideus.repository.mongo.MongoRecordRepository;
import com.ssafy.rideus.repository.redis.RedisRideRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.ssafy.rideus.common.exception.DuplicateException.GROUP_PARTICIPATE_DUPLICATE;
import static com.ssafy.rideus.common.exception.NotFoundException.RIDEROOM_NOT_FOUND;
import static com.ssafy.rideus.common.exception.NotFoundException.USER_NOT_FOUND;
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
        if (riddingType.equals(single)) {
            
        } else if (riddingType.equals(group)) {
            
        }
    }
}
