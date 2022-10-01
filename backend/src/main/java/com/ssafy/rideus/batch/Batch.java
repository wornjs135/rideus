package com.ssafy.rideus.batch;

import com.ssafy.rideus.domain.Course;
import com.ssafy.rideus.domain.Member;
import com.ssafy.rideus.hadoop.service.HadoopService;
import com.ssafy.rideus.repository.jpa.CourseRepository;
import com.ssafy.rideus.repository.jpa.MemberRepository;
import com.ssafy.rideus.service.BatchCourseService;
import com.ssafy.rideus.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class Batch {

    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;
    private final HadoopService hadoopService;

    private static String host = "http://localhost:8080/api";
    private static String totalTime = "/total/time";
    private static String totalDistance = "/total/distance";
    private static String totalSpeed = "/total/speed";
    private static String course = "/course";
    private static String memberTime = "/member/time";
    private static String memberDistance = "/member/distance";
    private static String memberSpeed = "/member/speed";

//    hadoop 3시에 새로운 코스 업데이트
//    @Scheduled(cron = "0 0 3 * * *") // 매일 오전 3시
//    public void setNewCourseHadoop() {
//        hadoopService.hadoopNearInfo();
//        log.info("새로운 코스 hadoop 갱신 완료");
//    }
//    // 코스 리뷰 뒤져서 상위 5개 저장하기(배치)
//    @Scheduled(cron = "0 0 3 * * *") // 매일 오전 3시
//    public void setCourseReviewTop5() {
//        batchCourseService.setCourseReviewTop5();
//        batchCourseService.setMemberReviewTop5();
//        log.info("리뷰 태그 상위 5개(코스, 회원) 갱신 완료");
//    }
//
//    // 랭킹 갱신
//    @Scheduled(cron = "0 0 4 ? ? 1") // 매주 월요일 오전 4시
//    public void updateRanking() {
//        // cache 초기화
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.delete(host + totalTime);
//        restTemplate.delete(host + totalDistance);
//        restTemplate.delete(host + totalSpeed);
//        restTemplate.delete(host + course);
//        restTemplate.delete(host + memberTime);
//        restTemplate.delete(host + memberDistance);
//        restTemplate.delete(host + memberSpeed);
//
//        // 다시 조회해서 갱신하기
//        List<Member> allMember = memberRepository.findAll();
//        for (Member member : allMember) {
//            restTemplate.getForObject(host + memberTime + "/" + member.getId(), String.class);
//            restTemplate.getForObject(host + memberDistance + "/" + member.getId(), String.class);
//            restTemplate.getForObject(host + memberSpeed + "/" + member.getId(), String.class);
//        }
//
//        List<Course> allCourse = courseRepository.findAll();
//        for (Course course : allCourse) {
//            restTemplate.getForObject(host + course + "/" + course.getId(), String.class);
//        }
//
//    }

}
