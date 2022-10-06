package com.ssafy.rideus.repository.mongo;

import com.ssafy.rideus.domain.collection.MongoRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoRecordRepository extends MongoRepository<MongoRecord, String> {
}
