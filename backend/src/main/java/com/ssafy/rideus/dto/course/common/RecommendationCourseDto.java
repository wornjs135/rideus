package com.ssafy.rideus.dto.course.common;

import com.ssafy.rideus.dto.tag.common.TagDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationCourseDto {

    private String courseId;
    private String courseName;
    private Double distance;
    private Integer expectedTime;
    private String start;
    private String finish;
    private Integer likeCount;
    private String imageUrl;
    private String category;
    private String bookmarkId;
    
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
    
    
    public static RecommendationCourseDto find(RecommendationCourseDtoInterface list, List<TagDto> tags) {
    	RecommendationCourseDto recommendationCourseDto = new RecommendationCourseDto();
    	recommendationCourseDto.courseId = list.getCourseId();
        recommendationCourseDto.courseName = list.getCourseName();
        recommendationCourseDto.distance = list.getDistance();
        recommendationCourseDto.expectedTime = list.getExpectedTime();
        recommendationCourseDto.start = list.getStart();
        recommendationCourseDto.finish = list.getFinish();
        recommendationCourseDto.likeCount = list.getLikeCount();
        recommendationCourseDto.imageUrl = list.getImageUrl();
        recommendationCourseDto.category = list.getCategory();
        recommendationCourseDto.bookmarkId = list.getBookmarkId();
        recommendationCourseDto.tags = tags;
        
    	return recommendationCourseDto;
    }
}
