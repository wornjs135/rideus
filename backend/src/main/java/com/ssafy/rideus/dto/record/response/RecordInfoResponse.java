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
    private String courseName;

    private String courseId;
    private Boolean isCourse;

    private Long rideRoomId;
    private List<ParticipantDto> participants = new ArrayList<>();

    private List<Coordinate> coordinates = new ArrayList<>();

    public static RecordInfoResponse from(Record record, MongoRecord mongoRecord) {
        RecordInfoResponse recordInfoResponse = new RecordInfoResponse();
        recordInfoResponse.recordId = record.getId();
        recordInfoResponse.recordDistance = record.getRecordDistance();
        recordInfoResponse.recordTime = record.getRecordTime();
        recordInfoResponse.recordSpeedAvg = record.getRecordSpeedAvg();
        recordInfoResponse.recordSpeedBest = record.getRecordSpeedBest();
        recordInfoResponse.recordTimeMinute = record.getRecordTimeMinute();
        recordInfoResponse.recordIsFinished = record.getRecordIsFinished();
        recordInfoResponse.recordIsMine = record.getRecordIsMine();
        recordInfoResponse.courseId =record.getCourse().getId() == null ? null : record.getCourse().getId();
        recordInfoResponse.isCourse =record.getCourse().getId() == null ? false : true;
        recordInfoResponse.rideRoomId =record.getRideRoom().getId();
        recordInfoResponse.participants =mongoRecord.getParticipants();
        recordInfoResponse.coordinates =mongoRecord.getCoordinates();
        recordInfoResponse.courseName = record.getCourse().getCourseName();

        return recordInfoResponse;
    }
}
