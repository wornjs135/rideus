package com.ssafy.rideus.dto.record.response;

import com.ssafy.rideus.domain.Record;
import com.ssafy.rideus.domain.base.Coordinate;
import com.ssafy.rideus.domain.collection.MongoRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyRideRecordRes {

    private String recordId;

    private String courseName;

    private Long roomId;

    private double distance;

    private String startedLocation;

    private String finishedLocation;

    private int expectedTime;

    private boolean isShared;

    private Double avgSpeed;

    private Double topSpeed;

    private double nowTime;

    private boolean isSingle;

    private List<Coordinate> latlng;

    public static MyRideRecordRes sharedMyRide(Record record) {
        return MyRideRecordRes.builder()
                .recordId(record.getId())
                .courseName(record.getCourse().getCourseName())
                .roomId(record.getRideRoom().getId())
                .distance(record.getCourse().getDistance())
                .startedLocation(record.getCourse().getStart())
                .finishedLocation(record.getCourse().getFinish())
                .expectedTime(record.getCourse().getExpectedTime())
                .isShared(true)
                .build();
    }

    public static MyRideRecordRes unSharedMyRide(Record record) {
        return MyRideRecordRes.builder()
                .recordId(record.getId())
                .courseName(record.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))+" 주행기록")
                .roomId(record.getRideRoom().getId())
                .distance(record.getRecordDistance())
                .expectedTime(record.getRecordTimeMinute().intValue())
                .avgSpeed(record.getRecordSpeedAvg())
                .topSpeed(record.getRecordSpeedBest())
                .nowTime(record.getRecordTime())
                .isShared(false)
                .build();
    }

    public void setLatLng(MongoRecord mongoRecord) {
        latlng = mongoRecord.getCoordinates();
    }

    public void computeIsSingle(int participantCnt) {
        if (participantCnt > 1) this.isSingle = false;
        else this.isSingle = true;
    }

}
