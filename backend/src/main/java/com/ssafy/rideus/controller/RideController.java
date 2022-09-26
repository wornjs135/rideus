package com.ssafy.rideus.controller;

import com.ssafy.rideus.dto.record.request.FinishRiddingRequest;
import com.ssafy.rideus.dto.record.request.SaveCoordinatesRequest;
import com.ssafy.rideus.dto.record.response.CreateRecordResponse;
import com.ssafy.rideus.dto.record.type.RiddingType;
import com.ssafy.rideus.dto.rideroom.request.GroupRiddingRequest;
import com.ssafy.rideus.dto.rideroom.response.CreateRideRoomResponse;
import com.ssafy.rideus.dto.rideroom.response.GroupRiddingResponse;
import com.ssafy.rideus.config.security.util.JwtUtil;
import com.ssafy.rideus.config.web.LoginMember;
import com.ssafy.rideus.domain.Member;
import com.ssafy.rideus.service.RideService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

import static com.ssafy.rideus.dto.rideroom.type.SocketMessageType.CURRENT_POSITION;
import static com.ssafy.rideus.dto.rideroom.type.SocketMessageType.ENTER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ride")
public class RideController {

    private final RideService rideService;

    private final SimpMessageSendingOperations messagingTemplate;
    private final JwtUtil jwtUtil;

    static final String SOCKET_SUBSCRIBE_BASE_URI = "/sub/ride/room/";

    // 그룹 라이딩 방 생성
    @PostMapping("/room")
    public ResponseEntity<CreateRideRoomResponse> createRideRoom(@ApiIgnore @LoginMember Member member) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rideService.createRiddingRoom(member));
    }

    // 그룹 주행 관련 웹소켓 기능
    @MessageMapping("/ride/group")
    public void rideWithGroup(GroupRiddingRequest request, @Header(HttpHeaders.AUTHORIZATION) String bearerToken) {
        Long memberId = Long.valueOf(jwtUtil.getSubject(bearerToken.substring(7)));
        log.debug(request.toString());
        log.debug(bearerToken);

        GroupRiddingResponse groupRiddingResponse = new GroupRiddingResponse();
        if (ENTER.equals(request.getMessageType())) { // 그룹 방에 입장
            messagingTemplate.convertAndSend(SOCKET_SUBSCRIBE_BASE_URI + request.getRideRoomId(), rideService.enterRiddingRoom(memberId, request));
        } else if (CURRENT_POSITION.equals(request.getMessageType())) { // 현재 내 위치 뿌리기
            groupRiddingResponse = rideService.searchMemberInfo(memberId);
            groupRiddingResponse.setLat(request.getLat());
            groupRiddingResponse.setLng(request.getLng());
            messagingTemplate.convertAndSend(SOCKET_SUBSCRIBE_BASE_URI + request.getRideRoomId(), groupRiddingResponse);
        }
    }

    // 주행 시작
    @PostMapping("/start")
    public ResponseEntity<CreateRecordResponse> startRidding(@ApiIgnore @LoginMember Member member) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rideService.startRidding(member));
    }

    // 주행 종료
    @PostMapping("/finish/{riddingType}")
    public ResponseEntity finishRidding(@ApiIgnore @LoginMember Member member, @PathVariable RiddingType riddingType,
                                        @RequestBody FinishRiddingRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(rideService.finishRidding(member, riddingType, request));
    }

    // 중간 주행 좌표 리스트들 저장
    @PostMapping("/save/{recordId}")
    public ResponseEntity saveCoordinatesPerPeriod(@ApiIgnore @LoginMember Member member, @PathVariable String recordId,
                                                   @RequestBody SaveCoordinatesRequest saveCoordinatesRequest) {
        rideService.saveCoordinatesPerPeriod(member, recordId, saveCoordinatesRequest);

        return ResponseEntity.ok().build();
    }

}
