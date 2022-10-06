package com.ssafy.rideus.dto.record;

import com.ssafy.rideus.domain.Course;
import com.ssafy.rideus.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordDto {
    private Long id;

    private Double recordDistance;

    private Long recordTime;

    private String recordSpeedAvg;

    private String recordSpeedBest;
}
