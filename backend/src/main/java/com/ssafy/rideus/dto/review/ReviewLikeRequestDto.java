package com.ssafy.rideus.dto.review;

import com.ssafy.rideus.domain.Review;
import com.ssafy.rideus.domain.ReviewLike;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewLikeRequestDto {
    private Long rid; //review id

    public static ReviewLikeRequestDto reviewLikeReq(Review review) {
        return ReviewLikeRequestDto.builder()
                .rid(review.getId())
                .build();
    }
}
