package com.ssafy.rideus.common.dto.rideroom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedisParticipantDto {

    private Long memberId;
    private String nickname;
}
