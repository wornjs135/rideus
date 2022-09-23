package com.ssafy.rideus.dto;

import com.ssafy.rideus.domain.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseReviewDto {

    private String courseId;
    private String courseName;
    private List<ReviewDto> reviewDtos;

    public CourseReviewDto(Course course) {
        this.courseId = course.getId();
        this.courseName = course.getCourseName();
        this.reviewDtos = course.getReviews().stream().map(review -> new ReviewDto(review)).collect(Collectors.toList());
    }
}
