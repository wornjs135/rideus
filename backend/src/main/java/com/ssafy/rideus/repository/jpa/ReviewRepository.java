package com.ssafy.rideus.repository.jpa;

import com.ssafy.rideus.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByCourseIdOrderByCreatedDateDesc(String id);

    @Query("select r from Review r join fetch r.member join fetch r.record")
    Optional<Review> findReviewWithMemberAndRecordById(Long reviewId);
}
