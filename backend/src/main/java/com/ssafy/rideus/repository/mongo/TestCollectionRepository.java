package com.ssafy.rideus.repository.mongo;

import com.ssafy.rideus.domain.collection.TestCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestCollectionRepository extends MongoRepository<TestCollection, String> {
}
