package com.ssafy.rideus.dto.review;

import com.ssafy.rideus.domain.Review;
import com.ssafy.rideus.domain.ReviewTag;
import com.ssafy.rideus.domain.Tag;
import com.ssafy.rideus.dto.ReviewTagDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDetailResponseDto {
    private Long mid;
    private int score;
    private String content;
    private String imageUrl;
    private List<ReviewTagDto> tags;
    private int likeCount;

    public static ReviewDetailResponseDto reviewDetailRes(Review review) {
        List<ReviewTag> reviewTags = review.getReviewTags();
        List<ReviewTagDto> result = new ArrayList<>();
        for (ReviewTag reviewTag : reviewTags) {
            result.add(ReviewTagDto.reviewTagRes(reviewTag));
        }
        return ReviewDetailResponseDto.builder()
                .mid(review.getMember().getId())
                .score(review.getScore())
                .content(review.getContent())
                .imageUrl(review.getImageUrl())
                .tags(result)
                .likeCount(review.getLikeCount())
                .build();
    }

}
