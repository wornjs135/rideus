package com.ssafy.rideus.repository.jpa;

import com.ssafy.rideus.domain.Course;
import com.ssafy.rideus.dto.course.common.CourseReviewTagTop5DtoInterface;
import com.ssafy.rideus.dto.course.common.RecommendationCourseDtoInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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

    @Query(value = "select distinct c.course_id courseId, c.course_name courseName, c.distance distance, c.start start, c.finish finish, c.expected_time expectedTime, c.like_count likeCount, a.tag_id tagId,  t.tag_name tagName, max(matchCnt) max, bookmark_id bookmarkId, c.image_url imageUrl, c.category\n" +
            "from (select course_id, tag_id, @eq_cnt \\:= IF(tag_id in (SELECT tag_id FROM member_tag\n" +
            "\t\t\t\t\t\t\t\t\t\t\twhere member_id = :memberId) && @current_course_id2 = course_id, @eq_cnt + 1, 1) as matchCnt,\n" +
            "\t\t@current_course_id2 \\:= course_id\n" +
            "\t\tfrom course_tag\n" +
            "\t\tgroup by course_id, tag_id) a join course c on a.course_id = c.course_id join tag t on a.tag_id = t.tag_id left outer join (SELECT course_id, bookmark_id FROM bookmark WHERE member_id = :memberId) b on c.course_id = b.course_id\n" +
            "group by a.course_id, a.tag_id\n" +
            "order by max desc", nativeQuery = true)
    List<RecommendationCourseDtoInterface> getRecommendationCourseByMemberId(@Param("memberId") Long memberId);

    @Query("select distinct c from Course c join fetch c.courseTags ct join fetch ct.tag order by c.likeCount desc ")
    List<Course> findAllOrderByLikeCount();

    
    // 전체 리스트 조회 (등록 최신 순)
    @Query(value = "SELECT c.course_id courseId, course_name courseName, distance, expected_time expectedTime, start, finish, like_count likeCount, image_url imageUrl, category, bookmark_id bookmarkId FROM course c LEFT OUTER JOIN (SELECT course_id, bookmark_id FROM bookmark WHERE member_id = :memberId) b ON c.course_id = b.course_id ORDER BY created_date DESC", nativeQuery = true)
    List<RecommendationCourseDtoInterface> getAllCourses(@Param("memberId") Long memberId);
    // 코스에 해당하는 태그 가져오기 (전체 리스트 조회 시 사용하기 위해)
    @Query(value = "SELECT ct.course_id courseId, ct.tag_id tagId, t.tag_name tagName FROM course_tag ct JOIN tag t ON ct.tag_id = t.tag_id;", nativeQuery = true)
    List<CourseReviewTagTop5DtoInterface> getAllCourseTags();
    
    // 특정 코스 조회 (courseIds에 해당하는 코스)
    // 상세 조회, 코스 검색, 코스 추천 시 사용
    @Query(value = "SELECT c.course_id courseId, course_name courseName, distance, expected_time expectedTime, start, finish, like_count likeCount, image_url imageUrl, category, bookmark_id bookmarkId FROM course c LEFT OUTER JOIN (SELECT course_id, bookmark_id FROM bookmark WHERE member_id = :memberId) b ON c.course_id = b.course_id WHERE c.course_id IN (:courseIds) ORDER BY created_date DESC", nativeQuery = true)
    List<RecommendationCourseDtoInterface> getSpecificCourse(@Param("memberId") Long memberId, @Param("courseIds") List<String> courseIds);
    // 특정 코스에 해당하는 태그 가져오기
    @Query(value = "SELECT ct.course_id courseId, ct.tag_id tagId, t.tag_name tagName FROM course_tag ct JOIN tag t ON ct.tag_id = t.tag_id WHERE ct.course_id IN (:courseIds)", nativeQuery = true)
    List<CourseReviewTagTop5DtoInterface> getSpecificCourseTags(@Param("courseIds") List<String> courseIds);
    
    // 특정 리뷰 평점
    @Query(value = "SELECT CONCAT(sum, '/', count) result FROM (SELECT COUNT(*) count, SUM(score) sum FROM review WHERE course_id = :courseId) tmp", nativeQuery = true)
    String getStarAvg(@Param("courseId") String courseId);
    
    
    // keyword 포함한 코스 식별자 리스트
    @Query(value = "SELECT course_id FROM course WHERE course_name LIKE %:keyword% OR start LIKE %:keyword%", nativeQuery = true)
    List<String> getAllCourseIdsByKeyword(@Param("keyword") String keyword);
    @Query(value = "SELECT course_id FROM course_tag ct JOIN tag t ON ct.tag_id = t.tag_id WHERE t.tag_name LIKE %:keyword%", nativeQuery = true)
    List<String> getAllCourseIdsByTagKeyword(@Param("keyword") String keyword);
    

    @Transactional(readOnly = false)
    @Modifying
    @Query("update Course c set c.category = :category where c.id = :id")
    void updateCourseCategoryByid(@Param("id") String id, @Param("category") String category);


    List<Course> findCoruseByCategoryNull();
}
