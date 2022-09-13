package com.ssafy.rideus.websocket;

import com.ssafy.rideus.common.dto.rideroom.request.GroupRiddingRequest;
import com.ssafy.rideus.common.dto.rideroom.response.GroupRiddingResponse;
import com.ssafy.rideus.common.dto.rideroom.type.SocketMessageType;

import static com.ssafy.rideus.common.dto.rideroom.type.SocketMessageType.CURRENT_POSITION;

public class TestInfo {

    public static final GroupRiddingResponse TEST_CURRENT_POSITION_RESPONSE = new GroupRiddingResponse(1L, "pjk", null, null);
    public static final GroupRiddingRequest TEST_CURRENT_POSITION_REQUEST = new GroupRiddingRequest(CURRENT_POSITION, 1L, "27.1313", "126.1313");
    public static final String TEST_AUTHORIZATION = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjYwMTA3OTc1LCJleHAiOjE2NjAxOTQzNzV9.dESecBt8K6RrhikhYM2HkHtmxCBvPLSTGKZcNzAQ5vI";
}
