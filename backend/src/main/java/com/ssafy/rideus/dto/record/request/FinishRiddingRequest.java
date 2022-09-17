package com.ssafy.rideus.dto.record.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinishRiddingRequest {

    private  Long courseId;
    private String distance;
    private String time;
    private String speedAvg;
    private String speedBest;

}
