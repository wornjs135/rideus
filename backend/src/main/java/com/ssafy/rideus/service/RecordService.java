package com.ssafy.rideus.service;

import com.ssafy.rideus.common.exception.NotFoundException;
import com.ssafy.rideus.domain.Record;
import com.ssafy.rideus.domain.collection.MongoRecord;
import com.ssafy.rideus.dto.record.response.RecordInfoResponse;
import com.ssafy.rideus.repository.jpa.RecordRepository;
import com.ssafy.rideus.repository.mongo.MongoRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ssafy.rideus.common.exception.NotFoundException.RECORD_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordService {

    private final RecordRepository recordRepository;
    private final MongoRecordRepository mongoRecordRepository;


    public RecordInfoResponse getRecordInfo(String recordId) {
        Record findRecord = recordRepository.findRecordWithCourseAndRideRoomAndMember(recordId)
                .orElseThrow(() -> new NotFoundException(RECORD_NOT_FOUND));
        MongoRecord findMongoRecord = mongoRecordRepository.findById(recordId)
                .orElseThrow(() -> new NotFoundException(RECORD_NOT_FOUND));

        return RecordInfoResponse.from(findRecord, findMongoRecord);
    }
}
