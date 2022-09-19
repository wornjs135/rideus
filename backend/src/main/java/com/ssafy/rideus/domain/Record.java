package com.ssafy.rideus.domain;

import com.ssafy.rideus.domain.base.BaseEntity;
import com.ssafy.rideus.dto.record.request.FinishRiddingRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    public static Record from(Member findMember, Course findCourse, FinishRiddingRequest request) {
        Record record = new Record();
        record.id = request.getRecordId();
        record.recordDistance = request.getDistance();
        record.recordTime = request.getTime();
        record.recordTimeMinute = request.getTimeMinute();
        record.recordSpeedAvg = request.getSpeedAvg();
        record.recordSpeedBest = request.getSpeedBest();
        record.member = findMember;
        record.course = findCourse;

        return record;
    }
}
