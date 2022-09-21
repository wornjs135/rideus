package com.ssafy.rideus.dto.record.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordTotalResponse {
    private Long total_time;
    private Double total_distance;
}
