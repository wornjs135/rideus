package com.ssafy.rideus.controller;

import com.ssafy.rideus.config.web.LoginMember;
import com.ssafy.rideus.domain.Member;
import com.ssafy.rideus.dto.course.common.RecommendationCourseDto;
import com.ssafy.rideus.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    // 추천 코스(리뷰 + 코스 태그 기반)
    @GetMapping("/recommendation")
    public ResponseEntity<List<RecommendationCourseDto>> getRecommendationCourseByTag(@ApiIgnore @LoginMember Member member) {
//        public ResponseEntity<List<RecommendationCourseDto>> getRecommendationCourseByTag() {
        return ResponseEntity.ok(courseService.getRecommendationCourseByTag(member.getId()));
    }
}
