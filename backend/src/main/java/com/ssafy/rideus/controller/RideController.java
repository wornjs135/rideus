package com.ssafy.rideus.controller;

import com.ssafy.rideus.common.dto.rideroom.request.GroupRiddingRequest;
import com.ssafy.rideus.common.dto.rideroom.response.GroupRiddingResponse;
import com.ssafy.rideus.common.dto.rideroom.type.SocketMessageType;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import static com.ssafy.rideus.common.dto.rideroom.type.SocketMessageType.CURRENT_POSITION;
import static com.ssafy.rideus.common.dto.rideroom.type.SocketMessageType.ENTER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ride")
public class RideController {

    private final RideService rideService;

    private final SimpMessageSendingOperations messagingTemplate;
    private final JwtUtil jwtUtil;

    // 그룹 라이딩 방 생성
    @PostMapping("/room")
    public ResponseEntity createRideRoom(@ApiIgnore @LoginMember Member member) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rideService.createRideRoom(member));
    }

    // 그룹 주행 관련 웹소켓 기능
    @MessageMapping("/ride/group")
    public void rideWithGroup(GroupRiddingRequest request, @Header(HttpHeaders.AUTHORIZATION) String bearerToken) {
        Long memberId = Long.valueOf(jwtUtil.getSubject(bearerToken.substring(7)));
        log.debug(request.toString());
        log.debug(bearerToken);

        GroupRiddingResponse groupRiddingResponse = new GroupRiddingResponse();
        if (ENTER.equals(request.getMessageType())) {

        } else if (CURRENT_POSITION.equals(request.getMessageType())) { // 현재 내 위치 뿌리기
            groupRiddingResponse = rideService.searchMemberInfo(memberId);
            groupRiddingResponse.setLat(request.getLat());
            groupRiddingResponse.setLng(request.getLng());
            messagingTemplate.convertAndSend("/sub/ride/room/" + request.getRideRoomId(), groupRiddingResponse);
        }
    }

}
