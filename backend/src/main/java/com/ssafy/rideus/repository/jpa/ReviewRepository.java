package com.ssafy.rideus.repository.jpa;

import com.ssafy.rideus.repository.jpa.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
