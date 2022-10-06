package com.ssafy.rideus.dto.bookmark.response;


import com.ssafy.rideus.domain.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookmarkCourseRes {

    private String courseId;

    private String courseName;

    private Double distance;

    private Integer expectedTime;

    private String startedLocation;

    private String finishedLocation;

    public static BookmarkCourseRes of(Course course) {
        return BookmarkCourseRes.builder()
                .courseId(course.getId())
                .courseName(course.getCourseName())
                .distance(course.getDistance())
                .expectedTime(course.getExpectedTime())
                .startedLocation(course.getStart())
                .finishedLocation(course.getFinish())
                .build();
    }
}
