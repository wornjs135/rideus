package com.ssafy.rideus.dto.review;

import com.ssafy.rideus.domain.*;
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
public class ReviewResponseDto {
    private String memberNickname;
    private String memberProfileImage;
    private int score;
    private String content;
    private String imageUrl;
    private List<ReviewTagDto> tags;
    private int likeCount;

    public static ReviewResponseDto reviewRes(Review review) {
        List<ReviewTag> reviewTags = review.getReviewTags();
        List<ReviewTagDto> result = new ArrayList<>();
        for (ReviewTag reviewTag : reviewTags) {
            result.add(ReviewTagDto.reviewTagRes(reviewTag));
        }
        return ReviewResponseDto.builder()
                .memberNickname(review.getMember().getNickname())
                .memberProfileImage(review.getMember().getProfileImageUrl())
                .score(review.getScore())
                .content(review.getContent())
                .imageUrl(review.getImageUrl())
                .tags(result)
                .likeCount(review.getLikeCount())
                .build();
    }
}
