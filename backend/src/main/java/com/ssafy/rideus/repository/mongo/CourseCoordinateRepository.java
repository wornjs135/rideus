package com.ssafy.rideus.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.ssafy.rideus.domain.collection.CourseCoordinate;

public interface CourseCoordinateRepository extends MongoRepository<CourseCoordinate, String> {
	

}
