package com.ssafy.rideus.repository.jpa;

import com.ssafy.rideus.domain.Member;
import com.ssafy.rideus.domain.Review;
import com.ssafy.rideus.domain.ReviewLike;
import com.ssafy.rideus.dto.review.ReviewLikeResDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
//    @Query("SELECT r.reviewLikeId FROM ReviewLike r WHERE(r.reviewId=:rid, r.memberId=:mid)")
//    int findReviewLikeIdByMemberIdAndReviewId(Long mid, Long rid);
    Optional<ReviewLike> findByMemberIdAndReviewId(Long mid, Long rid);
//    void saveReviewLike(ReviewLikeResDto reviewLikeResDto);
}
