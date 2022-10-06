package com.ssafy.rideus.controller;

import com.ssafy.rideus.config.security.auth.CustomUserDetails;
import com.ssafy.rideus.config.web.LoginMember;
import com.ssafy.rideus.domain.Member;
import com.ssafy.rideus.dto.course.common.RecommendationCourseDto;
import com.ssafy.rideus.dto.member.request.MemberMoreInfoReq;
import com.ssafy.rideus.domain.Course;
import com.ssafy.rideus.domain.collection.CourseCoordinate;
import com.ssafy.rideus.domain.collection.TestCollection;
import com.ssafy.rideus.repository.jpa.MemberRepository;
import com.ssafy.rideus.repository.mongo.TestCollectionRepository;
import com.ssafy.rideus.service.CourseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api("코스  API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
@Slf4j
public class CourseController {
	
    private final CourseService courseService;

    // 추천 코스(리뷰 + 코스 태그 기반)
    @GetMapping("/recommendation")
    public ResponseEntity<List<RecommendationCourseDto>> getRecommendationCourseByTag(@ApiIgnore @AuthenticationPrincipal CustomUserDetails member) {
		return ResponseEntity.ok(courseService.getRecommendationCourseByTag(member.getId()));
    }

		
	// 추천 코스 리스트 조회
	@GetMapping()
	public ResponseEntity<List<RecommendationCourseDto>> getAllCourses(@ApiIgnore @AuthenticationPrincipal CustomUserDetails member) {

		List<RecommendationCourseDto> courseList = courseService.getAllCourses(member.getId());
		return ResponseEntity.status(HttpStatus.OK).body(courseList);
	}
	
	
	// 추천 코스 상세 조회
	@GetMapping("/{courseId}")
	public ResponseEntity<Map<String, Object>> detail(@ApiIgnore @AuthenticationPrincipal CustomUserDetails member, @PathVariable String courseId) {
	
		Map<String, Object> course = courseService.getCourse(member.getId(), courseId);
		
		return ResponseEntity.status(HttpStatus.OK).body(course);
	}
	
	// 코스 검색
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<RecommendationCourseDto>> search(@ApiIgnore @AuthenticationPrincipal CustomUserDetails member, @PathVariable String keyword) {
		
		List<RecommendationCourseDto> courseList = courseService.getAllCoursesByKeyword(member.getId(), keyword);
		return ResponseEntity.status(HttpStatus.OK).body(courseList);
	}

	
	// 코스 추가 (사용자가 탄 코스 추가하는 경우)
	@PostMapping("/add")
	public ResponseEntity<String> add(@ApiIgnore @AuthenticationPrincipal CustomUserDetails member,
									   @RequestPart Map<String, String> inputMap, @RequestPart MultipartFile image) {

		log.info("코스 등록 데이터: " + inputMap);
		String result = courseService.addCourseData(inputMap, member.getId(), image);

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	
	// 추천 코스 크롤링 데이터 넣기
	@ApiOperation(value = "크롤링한 코스 데이터 추가")
	@PostMapping("/add/crawlingData")
	public ResponseEntity<Void> addCrawlingData() {

		try {
			courseService.addCrawlingData();
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(">>> addCrawlingData Exception: "+e.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}
	
}
