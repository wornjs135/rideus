package com.ssafy.rideus.dto.rideroom.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRideRoomResponse {

    @ApiModelProperty(notes = "그룹 라이딩 방 식별자")
    private Long rideRoomId;

    private String courseId;
    private String nickname;

    public static CreateRideRoomResponse from(Long rideRoomId, String courseId, String nickname) {
        CreateRideRoomResponse response = new CreateRideRoomResponse();
        response.rideRoomId = rideRoomId;
        response.courseId = courseId;
        response.nickname = nickname;

        return response;
    }
}
