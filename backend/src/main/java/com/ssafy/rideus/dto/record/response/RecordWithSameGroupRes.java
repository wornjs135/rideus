package com.ssafy.rideus.dto.record.response;

import com.ssafy.rideus.domain.Record;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordWithSameGroupRes {

    private Long recordTimeMinute;

    private Double recordSpeedBest;

    private Double recordSpeedAvg;

    private List<RecordForStatisticRes> records = new ArrayList<>();

    public void myRecord(Long recordTimeMinute, Double recordSpeedBest, Double recordSpeedAvg) {
        this.recordTimeMinute = recordTimeMinute;
        this.recordSpeedBest = recordSpeedBest;
        this.recordSpeedAvg = recordSpeedAvg;
    }

    public void addRecords(Record record, int rank) {
        RecordForStatisticRes recordForStatisticRes = RecordForStatisticRes.builder()
                .nickname(record.getMember().getNickname())
                .totalDistance(record.getRecordDistance())
                .recordSpeedAvg(record.getRecordSpeedAvg())
                .recordSpeedBest(record.getRecordSpeedBest())
                .recordTimeMinute(record.getRecordTimeMinute())
                .rank(rank)
                .build();
        this.records.add(recordForStatisticRes);
    }

}
