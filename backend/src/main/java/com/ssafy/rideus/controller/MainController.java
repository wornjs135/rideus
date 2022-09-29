package com.ssafy.rideus.controller;

import com.ssafy.rideus.config.security.auth.CustomUserDetails;
import com.ssafy.rideus.dto.course.common.RecommendationCourseDto;
import com.ssafy.rideus.dto.tag.common.TagDto;
import com.ssafy.rideus.service.CourseService;
import com.ssafy.rideus.service.ReviewTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import springfox.documentation.annotations.ApiIgnore;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ssafy.rideus.config.data.CacheKey.POPULARITY_TAG;

@RestController
@RequiredArgsConstructor
@RequestMapping("/popular")
public class MainController {

    private final CourseService courseService;
    private final ReviewTagService reviewTagService;

    // 인기 코스 가져오기(북마크 개수 순)
    @GetMapping("/course")
    public ResponseEntity<?> getPopularityCourse(@ApiIgnore @AuthenticationPrincipal CustomUserDetails member) {
        if (member == null) {
            return ResponseEntity.ok(courseService.getPopularityCourseWithBookmarkWithoutBookmark());
        } else {
            return ResponseEntity.ok(courseService.getPopularityCourseWithBookmark(1L));
        }
    }

    @GetMapping("/tag")
    @Cacheable(value = POPULARITY_TAG, key = "0", unless = "#result == null", cacheManager = "cacheManager")
    // 인기 태그 가져오기(리뷰 뒤져서 상위 5개)
    public List<TagDto> getPopularityTag() {
        return reviewTagService.getPopularityTag();
    }

	// 현 위치 기반 코스 추천
	@GetMapping("/{lat}/{lng}")
	public ResponseEntity<List<RecommendationCourseDto>> recommendByLoc(@ApiIgnore @AuthenticationPrincipal CustomUserDetails member, @PathVariable Double lat, @PathVariable Double lng) {

		List<RecommendationCourseDto> courseList = courseService.getAllCoursesByLoc(member.getId(), lat, lng);
		return ResponseEntity.status(HttpStatus.OK).body(courseList);
	}
}
