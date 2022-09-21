package com.ssafy.rideus.service;

import com.ssafy.rideus.common.exception.BadRequestException;
import com.ssafy.rideus.domain.*;
import com.ssafy.rideus.dto.review.*;
import com.ssafy.rideus.repository.jpa.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final ReviewTagRepository reviewTagRepository;
    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;

    @Override
    @Transactional
    public Review writeReview(ReviewRequestDto reviewRequestDto, Long mid) {
        Course course = courseRepository.findById(reviewRequestDto.getCid()).orElseThrow(() -> new BadRequestException("유효하지 않은 코스입니다."));
        Member member = memberRepository.findById(mid).orElseThrow(() -> new BadRequestException("유효하지 않은 회원입니다."));
        Review review = Review.builder()
                .member(member)
                .course(course)
                .score(reviewRequestDto.getScore())
                .content(reviewRequestDto.getContent())
                .imageUrl(reviewRequestDto.getImageUrl())
                .build();
        reviewRepository.save(review);

        return review;
    }

    @Override
    @Transactional
    public List<Review> showAllReview(String cid) {
        List<Review> reviews = reviewRepository.findAllByCourseId(cid);
        return reviews;
    }

    @Override
    @Transactional
    public ReviewDetailResponseDto showReviewDetail(Long rid) {
        Review review = reviewRepository.findById(rid).orElseThrow(() -> new BadRequestException("유효하지 않은 리뷰입니다."));

        return ReviewDetailResponseDto.builder()
                .mid(review.getMember().getId())
                .content(review.getContent())
                .tags(getTags(review))
                .build();
    }

    @Override
    @Transactional
    public ReviewLikeCountDto likeClick(ReviewLikeRequestDto reviewLikeRequestDto, Long mid) {
        Member member = memberRepository.findById(mid).orElseThrow(() -> new BadRequestException("유효하지 않은 회원입니다."));
        Review review = reviewRepository.findById(reviewLikeRequestDto.getRid()).orElseThrow(() -> new BadRequestException("유효하지 않은 리뷰입니다."));
        Optional<ReviewLike> result = reviewLikeRepository.findByReviewAndMember(review, member);
        if (result.isPresent()) {
            review.decreaseLike();
            reviewLikeRepository.delete(result.get());
        } else {
            review.increaseLike();
            reviewLikeRepository.save(new ReviewLike(member, review));
        }

        return new ReviewLikeCountDto(review.getLikeCount());
    }

    private List<String> getTags(Review review) {
        List<Tag> tags = reviewTagRepository.findReviewTagsByReview(review);
        return tags.stream().map(Tag -> Tag.getTagName()).collect(Collectors.toList());
    }
}
