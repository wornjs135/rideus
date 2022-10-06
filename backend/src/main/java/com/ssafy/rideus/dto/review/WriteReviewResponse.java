package com.ssafy.rideus.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WriteReviewResponse {

    private Long reviewId;

    public static WriteReviewResponse from(Long reviewId) {
        WriteReviewResponse writeReviewResponse = new WriteReviewResponse();
        writeReviewResponse.reviewId = reviewId;

        return writeReviewResponse;
    }
}
