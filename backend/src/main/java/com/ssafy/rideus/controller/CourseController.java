package com.ssafy.rideus.controller;

import com.ssafy.rideus.config.web.LoginMember;
import com.ssafy.rideus.domain.Member;
import com.ssafy.rideus.dto.course.common.RecommendationCourseDto;
import com.ssafy.rideus.domain.Course;
import com.ssafy.rideus.domain.collection.CourseCoordinate;
import com.ssafy.rideus.domain.collection.TestCollection;
import com.ssafy.rideus.repository.jpa.MemberRepository;
import com.ssafy.rideus.repository.mongo.TestCollectionRepository;
import com.ssafy.rideus.service.CourseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

@Api("코스  API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {
	
    private final CourseService courseService;

    @GetMapping("/recommendation")
    public ResponseEntity<List<RecommendationCourseDto>> getRecommendationCourseByTag(@ApiIgnore @LoginMember Member member) {
//        public ResponseEntity<List<RecommendationCourseDto>> getRecommendationCourseByTag() {
        return ResponseEntity.ok(courseService.getRecommendationCourseByTag(member.getId()));
    }

	
//	@Autowired
//	private CourseService courseService;


	// 추천 코스 리스트 조회
	@GetMapping()
	public ResponseEntity<List<Course>> findAllCourses() {
	
		// MySQL의 course 테이블에서 데이터 가져오기
		// MongoDB에 있는 코스 좌표와 체크 포인트는 가져올 필요 없음
		List<Course> courseList = courseService.findAllCourses();
		return ResponseEntity.status(HttpStatus.OK).body(courseList);
	}
	
	// 추천 코스 상세 조회
	@GetMapping("/{courseId}")
	public ResponseEntity detail(@PathVariable String courseId) {
	
		// MongoDB에 있는 코스 좌표와 체크포인트 좌표 가져오기 필수
		CourseCoordinate courseCoordinate = courseService.findCourse(courseId);
		
		
		return ResponseEntity.status(HttpStatus.OK).body(courseCoordinate);
	}

	
	// 코스 검색
	@GetMapping("/search/{keyword}")
	public ResponseEntity search(@PathVariable String keyword) {
		
		
		
		return ResponseEntity.ok().build();
	}

	
	// 코스 추가 (사용자가 탄 코스 추가하는 경우)
	@PutMapping("/add")
//	public ResponseEntity add()
	
	
	
	// 현 위치 기반 코스 추천
	@GetMapping("/{lat}/{lon}")
	public ResponseEntity recommendByLoc(@PathVariable String keyword) {
		
		
		
		return ResponseEntity.ok().build();
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
