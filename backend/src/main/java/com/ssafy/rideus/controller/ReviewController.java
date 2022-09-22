package com.ssafy.rideus.controller;

import com.ssafy.rideus.config.security.auth.CustomUserDetails;
import com.ssafy.rideus.domain.Review;
import com.ssafy.rideus.dto.review.*;
import com.ssafy.rideus.repository.jpa.ReviewLikeRepository;
import com.ssafy.rideus.repository.jpa.ReviewRepository;
import com.ssafy.rideus.repository.jpa.ReviewTagRepository;
import com.ssafy.rideus.service.MemberService;
import com.ssafy.rideus.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final MemberService memberService;

    //리뷰 작성
    @PostMapping("/write")
    public ResponseEntity<?> writeReview(@RequestBody ReviewRequestDto reviewRequestDto, @ApiIgnore @AuthenticationPrincipal CustomUserDetails user) {
        reviewService.writeReview(reviewRequestDto, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    //코스 별 리뷰 목록
    @GetMapping("/{course_id}")
    public ResponseEntity<?> reviewAll(@PathVariable(value = "course_id") String cid) {
        List<Review> result = reviewService.showAllReview(cid);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    //리뷰 상세
    @GetMapping("/{course_id}/{review_id}")
    public ResponseEntity<?> reviewDetail(@PathVariable(value = "review_id") Long rid) {
        ReviewDetailResponseDto result = reviewService.showReviewDetail(rid);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    //리뷰 좋아요
    @PostMapping("/click")
    public ResponseEntity<?> likeClick(@RequestBody ReviewLikeRequestDto reviewLikeRequestDto, @ApiIgnore @AuthenticationPrincipal CustomUserDetails user) {
        ReviewLikeCountDto result = reviewService.likeClick(reviewLikeRequestDto, user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
