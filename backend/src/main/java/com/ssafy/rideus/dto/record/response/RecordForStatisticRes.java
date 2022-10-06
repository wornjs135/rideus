package com.ssafy.rideus.dto.record.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordForStatisticRes {

    private String nickname;

    private String profileImageUrl;

    private Long recordTimeMinute;

    private Double recordSpeedBest;

    private Double recordSpeedAvg;

    private Double totalDistance;

    private int rank;
}
