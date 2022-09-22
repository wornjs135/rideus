package com.ssafy.rideus.dto;

import com.ssafy.rideus.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private Long reviewId;
    private String content;
    private List<ReviewTagDto> reviewTags = new ArrayList<>();

    public ReviewDto(Review review) {
        this.reviewId = review.getId();
        this.content = review.getContent();
        this.reviewTags = review.getReviewTags().stream().map(reviewTag -> new ReviewTagDto(reviewTag)).collect(Collectors.toList());
    }
}
