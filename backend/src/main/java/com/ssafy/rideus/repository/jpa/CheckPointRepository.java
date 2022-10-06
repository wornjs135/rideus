package com.ssafy.rideus.repository.jpa;

import com.ssafy.rideus.domain.CheckPoint;
import com.ssafy.rideus.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckPointRepository extends JpaRepository<CheckPoint, Long> {
    public List<CheckPoint> findByCourse(Course course);
}
