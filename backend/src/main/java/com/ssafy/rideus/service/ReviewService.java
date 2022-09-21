package com.ssafy.rideus.service;

import com.ssafy.rideus.domain.Review;
import com.ssafy.rideus.dto.review.*;

import java.util.List;

public interface ReviewService {
    //mid 회원 번호, cid 코스 식별자, rid 리뷰 식별자

    //리뷰 작성
    Review writeReview(ReviewRequestDto reviewRequestDto, Long mid);
    //코스 별 리뷰 전체 목록
    List<Review> showAllReview(Long cid);
    //리뷰 상세
    ReviewDetailResponseDto showReviewDetail(Long rid);
    //리뷰 좋아요
    ReviewLikeCountDto likeClick(ReviewLikeRequestDto reviewLikeRequestDto, Long mid);

}
