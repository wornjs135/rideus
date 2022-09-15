package com.ssafy.rideus.repository.jpa;

import com.ssafy.rideus.domain.Review;
import com.ssafy.rideus.domain.ReviewTag;
import com.ssafy.rideus.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewTagRepository extends JpaRepository<ReviewTag, Long> {
    List<Tag> findReviewTagsByReview(Review review);
}
