package com.ssafy.rideus.dto;

import com.ssafy.rideus.domain.ReviewTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewTagDto {

    private Long reviewTagId;
    private Long tagId;
    private String tagName;

    public ReviewTagDto(ReviewTag reviewTag) {
        this.reviewTagId = reviewTag.getId();
        this.tagId = reviewTag.getTag().getId();
        this.tagName = reviewTag.getTag().getTagName();
    }
    public static ReviewTagDto reviewTagRes(ReviewTag reviewTag) {
        return ReviewTagDto.builder()
                .reviewTagId(reviewTag.getId())
                .tagId(reviewTag.getTag().getId())
                .tagName(reviewTag.getTag().getTagName())
                .build();
    }
}