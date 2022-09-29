package com.ssafy.rideus.dto.record.response;

import com.ssafy.rideus.domain.Record;
import com.ssafy.rideus.domain.base.Coordinate;
import com.ssafy.rideus.domain.collection.MongoRecord;
import com.ssafy.rideus.dto.rideroom.common.ParticipantDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordInfoResponse {

    private String recordId;
    private Double recordDistance;
    private Double recordTime;
    private Double recordSpeedAvg;
    private Double recordSpeedBest;
    private Long recordTimeMinute;
    private Boolean recordIsFinished;
    private Boolean recordIsMine;

    private String courseId;
    private Boolean isCourse;

    private Long rideRoomId;
    private List<ParticipantDto> participants = new ArrayList<>();

    private List<Coordinate> coordinates = new ArrayList<>();

    public static RecordInfoResponse from(Record record, MongoRecord mongoRecord) {
        return RecordInfoResponse.builder()
                .recordId(record.getId())
                .recordDistance(record.getRecordDistance())
                .recordTime(record.getRecordTime())
                .recordSpeedAvg(record.getRecordSpeedAvg())
                .recordSpeedBest(record.getRecordSpeedBest())
                .recordTimeMinute(record.getRecordTimeMinute())
                .recordIsFinished(record.getRecordIsFinished())
                .recordIsMine(record.getRecordIsMine())
                .courseId(record.getCourse().getId() == null ? null : record.getCourse().getId())
                .isCourse(record.getCourse().getId() == null ? false : true)
                .rideRoomId(record.getRideRoom().getId())
                .participants(mongoRecord.getParticipants())
                .coordinates(mongoRecord.getCoordinates()).build();


    }
}
