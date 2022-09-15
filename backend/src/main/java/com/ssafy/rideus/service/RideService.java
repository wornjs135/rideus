package com.ssafy.rideus.service;

import com.ssafy.rideus.common.dto.rideroom.request.GroupRiddingRequest;
import com.ssafy.rideus.common.dto.rideroom.response.GroupRiddingResponse;
import com.ssafy.rideus.common.exception.NotFoundException;
import com.ssafy.rideus.domain.Member;
import com.ssafy.rideus.domain.RideRoom;
import com.ssafy.rideus.repository.jpa.MemberRepository;
import com.ssafy.rideus.repository.jpa.RideRoomRepository;
import com.ssafy.rideus.repository.redis.RedisRideRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ssafy.rideus.common.exception.NotFoundException.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RideService {

    private final MemberRepository memberRepository;
    private final RideRoomRepository rideRoomRepository;
    private final RedisRideRoomRepository redisRideRoomRepository;

    public Long createRideRoom(Member member) {
        Member findMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        return rideRoomRepository.save(RideRoom.create(findMember)).getId();
    }

    public GroupRiddingResponse searchMemberInfo(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));

        GroupRiddingResponse groupRiddingResponse = new GroupRiddingResponse();
        groupRiddingResponse.setMemberId(findMember.getId());
        groupRiddingResponse.setNickname(findMember.getNickname());

        return groupRiddingResponse;
    }

    public void enterRideRoom(Long memberId, GroupRiddingRequest request) {

    }
}
