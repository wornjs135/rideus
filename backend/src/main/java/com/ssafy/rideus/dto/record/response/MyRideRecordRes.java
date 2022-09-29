package com.ssafy.rideus.dto.record.response;

import com.ssafy.rideus.domain.Record;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyRideRecordRes {

    private String recordId;

    private String courseName;

    private Long roomId;

    private double distance;

    private String finishedLocation;

    private int expectedTime;

    private boolean isShared;

    public static MyRideRecordRes sharedMyRide(Record record) {
        return MyRideRecordRes.builder()
                .recordId(record.getId())
                .courseName(record.getCourse().getCourseName())
                .roomId(record.getRideRoom().getId())
                .distance(record.getCourse().getDistance())
                .finishedLocation(record.getCourse().getFinish())
                .expectedTime(record.getCourse().getExpectedTime())
                .isShared(true)
                .build();
    }

    public static MyRideRecordRes unSharedMyRide(Record record) {
        return MyRideRecordRes.builder()
                .recordId(record.getId())
                .courseName(record.getCreatedDate().toString())
                .roomId(record.getRideRoom().getId())
                .distance(record.getRecordDistance())
                .finishedLocation(record.getCourse().getFinish())
                .expectedTime(record.getRecordTimeMinute().intValue())
                .isShared(false)
                .build();
    }

}
