package com.ssafy.rideus.repository.jpa;

import com.ssafy.rideus.domain.Member;
import com.ssafy.rideus.domain.Review;
import com.ssafy.rideus.domain.ReviewLike;
import com.ssafy.rideus.dto.review.ReviewLikeResDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
//    List<ReviewLike> findAllByMemberId(Long id);
//    void deleteLike(Long id);
    ReviewLike findReviewLikeByMemberIdAndReviewId(Long mid, Long rid);
}
