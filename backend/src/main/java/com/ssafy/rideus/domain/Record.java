package com.ssafy.rideus.domain;

import com.ssafy.rideus.domain.base.BaseEntity;
import com.ssafy.rideus.domain.base.Coordinate;
import com.ssafy.rideus.dto.record.request.FinishRiddingRequest;
import com.ssafy.rideus.service.CourseService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Record extends BaseEntity {

    @Id
    @Column(name = "record_id")
    private String id;

    private Double recordDistance;

    private Double recordTime;

    private Double recordSpeedAvg;

    private Double recordSpeedBest;

    private Long recordTimeMinute;

    private Boolean recordIsFinished;
    private Boolean recordIsMine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ride_room_id")
    private RideRoom rideRoom;

    public static Record from(Member findMember, Course findCourse, FinishRiddingRequest request, List<Coordinate> myCoordinates, List<Coordinate> courseCoordinates, RideRoom rideRoom) {
        Record record = new Record();
        record.id = request.getRecordId();
        record.recordDistance = request.getDistance();
        record.recordTime = request.getTime();
        record.recordTimeMinute = request.getTimeMinute();
        record.recordSpeedAvg = request.getSpeedAvg();
        record.recordSpeedBest = request.getSpeedBest();
        record.recordIsFinished = (findCourse == null) ? true : checkFinished(myCoordinates.get(myCoordinates.size()-1), courseCoordinates.get(courseCoordinates.size()-1));
        record.recordIsMine = (findCourse == null) ? true : false;
        record.member = findMember;
        record.course = findCourse;
        record.rideRoom = rideRoom;

        return record;
    }

    private static Boolean checkFinished(Coordinate myFinishCoordinate, Coordinate courseFinishCoordinate) {
        Boolean result = null;

        double lat1 = Double.parseDouble(myFinishCoordinate.getLat());
        double lng1 = Double.parseDouble(myFinishCoordinate.getLng());
        double lat2 = Double.parseDouble(courseFinishCoordinate.getLat());
        double lng2 = Double.parseDouble(courseFinishCoordinate.getLng());

        double distance = CourseService.intervalMeter(lat1, lng1, lat2, lng2);

        if (distance <= 500) {
            result = true;
        } else {
            result = false;
        }

        return result;
    }
}
