package com.ssafy.rideus.dto.rideroom.response;

import com.ssafy.rideus.domain.redish.RedisRideRoom;
import com.ssafy.rideus.dto.rideroom.common.ParticipantDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedisRideRoomResponse {

    private Long rideRoomid;
    private List<ParticipantDto> participants = new ArrayList<>();

    public static RedisRideRoomResponse from(RedisRideRoom redisRideRoom) {
        RedisRideRoomResponse response = new RedisRideRoomResponse();
        response.rideRoomid = redisRideRoom.getId();
        response.participants = redisRideRoom.getParticipants();

        return response;
    }
}
