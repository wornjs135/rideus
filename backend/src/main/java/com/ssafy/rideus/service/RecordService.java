package com.ssafy.rideus.service;

import com.ssafy.rideus.common.exception.NotFoundException;
import com.ssafy.rideus.domain.Record;
import com.ssafy.rideus.domain.collection.MongoRecord;
import com.ssafy.rideus.dto.record.response.RecordInfoResponse;
import com.ssafy.rideus.dto.record.response.RecordWithSameGroupRes;
import com.ssafy.rideus.repository.jpa.RecordRepository;
import com.ssafy.rideus.repository.mongo.MongoRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ssafy.rideus.common.exception.NotFoundException.RECORD_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RecordService {

    private final RecordRepository recordRepository;
    private final MongoRecordRepository mongoRecordRepository;


    public RecordInfoResponse getRecordInfo(String recordId) {
        Record findRecord = recordRepository.findRecordWithCourseAndRideRoomAndMember(recordId)
                .orElseThrow(() -> new NotFoundException(RECORD_NOT_FOUND));
        MongoRecord mongoRecord = mongoRecordRepository.findById(recordId)
                .orElseThrow(() -> new NotFoundException(RECORD_NOT_FOUND));

        return RecordInfoResponse.from(findRecord, mongoRecord);
    }

    public RecordWithSameGroupRes getRecordSameGroup(Long groupId, Long memberId) {
        List<Record> recordByRideRoomId = recordRepository.findRecordByRideRoomIdOrderByRecordDistanceDesc(groupId);
        RecordWithSameGroupRes recordWithSameGroupRes = new RecordWithSameGroupRes();
        int rank = 0;
        Double preDistance = -1D;
        for (Record record : recordByRideRoomId) {
            if (memberId.equals(record.getMember().getId())) {
                if (preDistance < record.getRecordDistance()) {
                    rank++;
                    preDistance = record.getRecordDistance();
                }
                recordWithSameGroupRes.myRecord(record.getRecordTimeMinute(), record.getRecordSpeedBest(), record.getRecordSpeedAvg());
            }
            recordWithSameGroupRes.addRecords(record,rank);
        }

        return recordWithSameGroupRes;
    }
}
