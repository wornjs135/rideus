package com.ssafy.rideus.repository.mongo;

import com.ssafy.rideus.domain.collection.NearInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NearInfoRepository extends MongoRepository<NearInfo, String> {
}
