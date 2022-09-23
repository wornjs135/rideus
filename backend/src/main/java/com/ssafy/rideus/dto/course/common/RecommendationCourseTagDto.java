package com.ssafy.rideus.dto.course.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationCourseTagDto {

    private Long tagId;
    private String tagName;

    public static RecommendationCourseTagDto from(Long tagId, String tagName) {
        RecommendationCourseTagDto recommendationCourseTagDto = new RecommendationCourseTagDto();
        recommendationCourseTagDto.tagId = tagId;
        recommendationCourseTagDto.tagName = tagName;

        return recommendationCourseTagDto;
    }
}
