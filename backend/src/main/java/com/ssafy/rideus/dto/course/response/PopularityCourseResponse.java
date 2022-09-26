package com.ssafy.rideus.dto.course.response;

import com.ssafy.rideus.domain.Course;
import com.ssafy.rideus.domain.CourseTag;
import com.ssafy.rideus.domain.Member;
import com.ssafy.rideus.domain.Review;
import com.ssafy.rideus.dto.tag.common.TagDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PopularityCourseResponse {

    private String courseId;
    private String courseName;
    private String distance;
    private String start;
    private String finish;
    private String expectedTime;
    private Integer likeCount;
    private List<TagDto> tags = new ArrayList<>();


    public static PopularityCourseResponse from(Course course) {
        PopularityCourseResponse popularityCourseResponse = new PopularityCourseResponse();
        popularityCourseResponse.courseId = course.getId();
        popularityCourseResponse.courseName = course.getCourseName();
        popularityCourseResponse.distance = course.getDistance();
        popularityCourseResponse.start = course.getStart();
        popularityCourseResponse.finish = course.getFinish();
        popularityCourseResponse.expectedTime = course.getExpectedTime();
        popularityCourseResponse.likeCount = course.getLikeCount();
        popularityCourseResponse.tags = course.getCourseTags().stream().map(courseTag -> TagDto.from(courseTag)).collect(Collectors.toList());

        return popularityCourseResponse;
    }
}
