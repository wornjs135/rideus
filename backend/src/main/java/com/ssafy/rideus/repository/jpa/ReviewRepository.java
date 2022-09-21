package com.ssafy.rideus.repository.jpa;

import com.ssafy.rideus.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByCourseId(String id);
}
