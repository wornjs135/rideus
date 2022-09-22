package com.ssafy.rideus.repository.jpa;

import com.ssafy.rideus.domain.Course;
import com.ssafy.rideus.dto.course.common.CourseReviewTagTop5DtoInterface;
import com.ssafy.rideus.dto.course.common.RecommendationCourseDtoInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, String> {

    @Query(value = "select course_id as courseId, tag_id as tagId " +
            "from (select  course_id, tag_id, cnt, " +
            "@cnt_rank \\:= IF(@current_course_id = course_id, @cnt_rank + 1, 1) as cnt_rank, " +
            "@current_course_id \\:= course_id " +
            "from " +
            "(SELECT c.course_id, tag_id, count(*) as cnt " +
            "from course c join review r on c.course_id = r.course_id join review_tag rt on r.review_id = rt.review_id " +
            "group by course_id, tag_id " +
            "order by cnt desc) a " +
            ") b " +
            "where cnt_rank <= 5;", nativeQuery = true)
    List<CourseReviewTagTop5DtoInterface> getCourseReviewTagTop5();

    @Query(value = "select distinct c.course_id as courseId, c.course_name as courseName, c.distance as distance, c.start as start, c.finish as finish, c.expected_time as expectedTime, c.like_count as likeCount, a.tag_id as tagId,  t.tag_name as tagName, max(matchCnt) as max\n" +
            "from (select course_id, tag_id, @eq_cnt \\:= IF(tag_id in (SELECT tag_id FROM member_tag\n" +
            "\t\t\t\t\t\t\t\t\t\t\twhere member_id = :memberId) && @current_course_id2 = course_id, @eq_cnt + 1, 1) as matchCnt,\n" +
            "\t\t@current_course_id2 \\:= course_id\n" +
            "\t\tfrom course_tag\n" +
            "\t\tgroup by course_id, tag_id) a join course c on a.course_id = c.course_id join tag t on a.tag_id = t.tag_id\n" +
            "group by a.course_id, a.tag_id\n" +
            "order by max desc", nativeQuery = true)
    List<RecommendationCourseDtoInterface> getRecommendationCourseByMemberId(@Param("memberId") Long memberId);
}
