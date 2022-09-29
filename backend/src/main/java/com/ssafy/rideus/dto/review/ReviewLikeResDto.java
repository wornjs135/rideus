package com.ssafy.rideus.dto.review;

import com.ssafy.rideus.domain.Review;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewLikeResDto {
    private Long rid; //review id
    private int count; //like count

    public static ReviewLikeResDto reviewLikeRes(Review review) {
        return ReviewLikeResDto.builder()
                .rid(review.getId())
                .count(review.getLikeCount())
                .build();
    }
}
