package com.ssafy.rideus.repository.redis;

import com.ssafy.rideus.repository.jpa.domain.redish.RedisRideRoom;
import org.springframework.data.repository.CrudRepository;

public interface RedisRideRoomRepository extends CrudRepository<RedisRideRoom, Long> {
}
