package com.ssafy.rideus.dto.course.common;

import com.ssafy.rideus.dto.tag.common.TagDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationCourseDto {

    private String courseId;
    private String courseName;
    private String distance;
    private String expectedTime;
    private String start;
    private String finish;
    private Integer likeCount;
    private List<TagDto> tags = new ArrayList<>();

    public static RecommendationCourseDto from(RecommendationCourseDtoInterface list) {
        RecommendationCourseDto recommendationCourseDto = new RecommendationCourseDto();
        recommendationCourseDto.courseId = list.getCourseId();
        recommendationCourseDto.courseName = list.getCourseName();
        recommendationCourseDto.distance = list.getDistance();
        recommendationCourseDto.expectedTime = list.getExpectedTime();
        recommendationCourseDto.start = list.getStart();
        recommendationCourseDto.finish = list.getFinish();
        recommendationCourseDto.likeCount = list.getLikeCount();
        recommendationCourseDto.tags.add(TagDto.from(list.getTagId(), list.getTagName()));

        return recommendationCourseDto;
    }

    public void addTags(Long tagId, String tagName) {
        this.tags.add(TagDto.from(tagId, tagName));
    }
}
