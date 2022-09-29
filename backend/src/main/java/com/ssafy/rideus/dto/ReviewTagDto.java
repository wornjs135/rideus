package com.ssafy.rideus.dto;

import com.ssafy.rideus.domain.ReviewTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewTagDto {

    private Long reviewTagId;
    private Long tagId;
    private String tagName;

    public ReviewTagDto(ReviewTag reviewTag) {
        this.reviewTagId = reviewTag.getId();
        this.tagId = reviewTag.getTag().getId();
        this.tagName = reviewTag.getTag().getTagName();
    }
}
