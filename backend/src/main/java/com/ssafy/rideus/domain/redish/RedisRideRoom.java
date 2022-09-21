package com.ssafy.rideus.domain.redish;

import com.ssafy.rideus.dto.rideroom.common.ParticipantDto;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@RedisHash(value = "ride_room_people", timeToLive = 60*60*24)
public class RedisRideRoom implements Serializable {

    @Id
    private Long id;

    private List<ParticipantDto> participants = new ArrayList<>();

    public static RedisRideRoom create(Long rideRoomId) {
        RedisRideRoom redisRideRoom = new RedisRideRoom();
        redisRideRoom.id = rideRoomId;

        return redisRideRoom;
    }
}
