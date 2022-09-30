package com.ssafy.rideus.controller;

import com.ssafy.rideus.config.security.auth.CustomUserDetails;
import com.ssafy.rideus.dto.review.*;
import com.ssafy.rideus.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    //리뷰 작성
    @PostMapping("/write")
    public ResponseEntity<WriteReviewResponse> writeReview(@RequestPart ReviewRequestDto reviewRequestDto, @RequestPart MultipartFile image,
                                         @ApiIgnore @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.writeReview(reviewRequestDto, user.getId(), image));
    }
    //코스 별 리뷰 목록
    @GetMapping("/all/{course_id}")
    public ResponseEntity<List<ReviewResponseDto>> reviewAll(@PathVariable(value = "course_id") String cid) {
        return new ResponseEntity<List<ReviewResponseDto>>(reviewService.showAllReview(cid), HttpStatus.OK);
    }
    //리뷰 상세
    @GetMapping("/detail/{review_id}")
    public ResponseEntity<ReviewDetailResponseDto> reviewDetail(@PathVariable(value = "review_id") Long rid) {
        ReviewDetailResponseDto result = reviewService.showReviewDetail(rid);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //리뷰 좋아요
    @PostMapping("/click")
    //@RequestBody ReviewLikeRequestDto reviewLikeRequestDto
    //, @ApiIgnore @AuthenticationPrincipal CustomUserDetails user
    //user.getId()
    public ResponseEntity<?> likeClick(Long rid, Long mid) {
        ReviewLikeResDto result = reviewService.likeClick(rid, 4L);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
