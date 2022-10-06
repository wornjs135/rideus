package com.ssafy.rideus.dto.record.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinishRiddingRequest {

    private String courseId;
    private String recordId;
    private Double distance;
    private Double time;
    private Long timeMinute;
    private Double speedAvg;
    private Double speedBest;
    private Long rideRoomId;

}
