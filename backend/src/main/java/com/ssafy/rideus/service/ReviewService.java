package com.ssafy.rideus.service;

import com.ssafy.rideus.common.api.S3Upload;
import com.ssafy.rideus.common.exception.BadRequestException;
import com.ssafy.rideus.common.exception.NotFoundException;
import com.ssafy.rideus.domain.*;
import com.ssafy.rideus.dto.review.*;
import com.ssafy.rideus.repository.jpa.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ssafy.rideus.common.exception.BadRequestException.NOT_REGISTED_COURSE;
import static com.ssafy.rideus.common.exception.NotFoundException.RECORD_NOT_FOUND;
import static com.ssafy.rideus.common.exception.NotFoundException.TAG_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final ReviewTagRepository reviewTagRepository;
    private final MemberRepository memberRepository;
    private final RecordRepository recordRepository;
    private final S3Upload s3Upload;
    private final TagRepository tagRepository;

    @Transactional
    public WriteReviewResponse writeReview(ReviewRequestDto reviewRequestDto, Long mid, MultipartFile image) {

        Record findRecord = recordRepository.findRecordWithCourseAndRideRoomAndMember(reviewRequestDto.getRecordId())
                .orElseThrow(() -> new NotFoundException(RECORD_NOT_FOUND));

        // 에러 처리
        if (findRecord.getCourse() == null) {
            throw new BadRequestException(NOT_REGISTED_COURSE);
        }

        Member findMember = memberRepository.findById(mid).orElseThrow(() -> new BadRequestException("유효하지 않은 회원입니다."));

        Review saveReview = reviewRepository.save(Review.createReview(reviewRequestDto, findMember, s3Upload.uploadImageToS3(image), findRecord));

        List<ReviewTagRequest> tags = reviewRequestDto.getTags();
        List<ReviewTag> reviewTags = new ArrayList<>();
        for (ReviewTagRequest tag : tags) {
            Tag findTag = tagRepository.findById(tag.getId())
                    .orElseThrow(() -> new NotFoundException(TAG_NOT_FOUND));
            reviewTags.add(ReviewTag.createReviewTag(saveReview, findTag));
        }
        reviewTagRepository.saveAll(reviewTags);

        return WriteReviewResponse.from(saveReview.getId());
    }

    @Transactional
    public List<ReviewResponseDto> showAllReview(String cid) {
        List<ReviewResponseDto> result = new ArrayList<>();
        List<Review> reviews = reviewRepository.findAllByCourseId(cid);
        for (Review review : reviews) {
            result.add(ReviewResponseDto.reviewRes(review));
        }
        return result;
    }

    @Transactional
    public ReviewDetailResponseDto showReviewDetail(Long rid) {
        Review review = reviewRepository.findById(rid).orElseThrow(() -> new BadRequestException("유효하지 않은 리뷰입니다."));
        return ReviewDetailResponseDto.reviewDetailRes(review);
    }

    @Transactional
    public ReviewLikeResDto likeClick(Long rid, Long mid) {
        Member member = memberRepository.findById(mid).orElseThrow(() -> new BadRequestException("유효하지 않은 회원입니다."));
        Review review = reviewRepository.findById(rid).orElseThrow(() -> new BadRequestException("유효하지 않은 리뷰입니다."));
        Optional<ReviewLike> result = reviewLikeRepository.findByMemberIdAndReviewId(mid, rid);
        if (!result.isPresent()) {
            review.increaseLike();
            reviewLikeRepository.save(new ReviewLike(member, review));
        } else {
            review.decreaseLike();
            reviewLikeRepository.delete(result.get());
        }
        return ReviewLikeResDto.reviewLikeRes(review);
    }

    private List<String> getTags(Review review) {
        List<Tag> tags = reviewTagRepository.findReviewTagsByReview(review);
        return tags.stream().map(Tag -> Tag.getTagName()).collect(Collectors.toList());
    }
}
