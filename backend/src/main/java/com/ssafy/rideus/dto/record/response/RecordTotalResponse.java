package com.ssafy.rideus.dto.record.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecordTotalResponse {
    private String total_time;
    private Double total_distance;

    public RecordTotalResponse(String total_time, Double total_distance) {
        this.total_time = total_time;
        this.total_distance = total_distance;
    }
}
