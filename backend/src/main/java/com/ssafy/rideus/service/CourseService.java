package com.ssafy.rideus.service;

import com.ssafy.rideus.domain.Course;
import com.ssafy.rideus.dto.course.common.RecommendationCourseDto;
import com.ssafy.rideus.dto.course.common.RecommendationCourseDtoInterface;
import com.ssafy.rideus.dto.course.response.PopularityCourseResponse;
import com.ssafy.rideus.repository.jpa.CourseRepository;
import com.ssafy.rideus.repository.jpa.MemberTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;
    private final MemberTagRepository memberTagRepository;

    public List<RecommendationCourseDto> getRecommendationCourseByTag(Long memberId) {
        List<RecommendationCourseDtoInterface> recommendationCourseByMemberId = courseRepository.getRecommendationCourseByMemberId(memberId);

        List<RecommendationCourseDto> recommendationCourseDtos = new ArrayList<>();
        for (RecommendationCourseDtoInterface r : recommendationCourseByMemberId) {

            if (recommendationCourseDtos.isEmpty()) {
                recommendationCourseDtos.add(RecommendationCourseDto.from(r));
            } else {
                RecommendationCourseDto lastCourse = recommendationCourseDtos.get(recommendationCourseDtos.size() - 1);
                if(lastCourse.getCourseId().equals(r.getCourseId())) {
                    lastCourse.addTags(r.getTagId(), r.getTagName());
                } else {
                    recommendationCourseDtos.add(RecommendationCourseDto.from(r));
                }
            }
        }

//        System.out.println(recommendationCourseDtos.size());
//        System.out.println(recommendationCourseDtos);

        return recommendationCourseDtos;
    }

    public List<PopularityCourseResponse> getPopularityCourse() {
        List<Course> popularityCourses = courseRepository.findAllOrderByLikeCount();

         return popularityCourses.stream().map(course -> PopularityCourseResponse.from(course)).collect(Collectors.toList());
    }
}
