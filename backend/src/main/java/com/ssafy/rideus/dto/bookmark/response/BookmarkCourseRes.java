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

    private Double distance;

    private Integer expectedTime;

    private String start;

    private String finish;

    public static BookmarkCourseRes of(Course course) {
        return BookmarkCourseRes.builder()
                .courseId(course.getId())
                .distance(course.getDistance())
                .expectedTime(course.getExpectedTime())
                .start(course.getStart())
                .finish(course.getFinish())
                .build();
    }
}
