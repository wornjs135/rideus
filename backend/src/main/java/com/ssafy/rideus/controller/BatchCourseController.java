package com.ssafy.rideus.controller;

import com.ssafy.rideus.service.BatchCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BatchCourseController {

    private final BatchCourseService batchCourseService;

    // 코스 리뷰 뒤져서 상위 5개 저장하기(배치)
    @GetMapping("/b/course/review")
    public void setCourseReviewTop5() {
        batchCourseService.setCourseReviewTop5();
    }

    // 멤버 리뷰 뒤져서 상위 5개 저장하기(배치)
    @GetMapping("/b/member/review")
    public void setMemberReviewTop5() {
        batchCourseService.setMemberReviewTop5();
    }
}
