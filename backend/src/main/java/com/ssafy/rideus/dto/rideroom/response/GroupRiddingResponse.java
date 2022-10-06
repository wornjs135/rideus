package com.ssafy.rideus.dto.rideroom.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupRiddingResponse {

    private Long memberId;
    private String nickname;
    private String lat;
    private String lng;
    private String profileImageUrl;
    private String color;
}
