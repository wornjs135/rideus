package com.ssafy.rideus.repository.jpa.domain.redish;

import com.ssafy.rideus.common.dto.rideroom.RedisParticipantDto;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Getter
@RedisHash(value = "ride_room_people", timeToLive = 60*60*24)
public class RedisRideRoom {

    @Id
    private Long id;

    private List<RedisParticipantDto> participants = new ArrayList<>();
}
