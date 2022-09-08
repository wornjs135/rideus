package com.ssafy.rideus.repository.jpa;

import com.ssafy.rideus.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
