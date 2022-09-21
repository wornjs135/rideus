package com.ssafy.rideus.controller;

import com.ssafy.rideus.config.web.LoginMember;
import com.ssafy.rideus.domain.Member;
import com.ssafy.rideus.dto.rank.response.*;
import com.ssafy.rideus.service.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

import static com.ssafy.rideus.config.data.CacheKey.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rank")
public class RankController {

    private final RankService rankService;

    // 전체 랭킹 시간별 조회
    @GetMapping("/total/time")
    @Cacheable(value = RANK_TOTAL_TIME, key = "0", unless = "#result == null", cacheManager = "cacheManager")
    public List<RankTimeResponseDto> getRankTotalTime() {
        return rankService.getRankTotalTime();
    }

    // 전체 랭킹 거리별 조회
    @GetMapping("/total/distance")
    @Cacheable(value = RANK_TOTAL_DISTANCE, key = "0", unless = "#result == null", cacheManager = "cacheManager")
    public List<RankDistanceResponseDto> getRankTotalDistance() {
        return rankService.getRankTotalDistance();
    }

    // 전체 랭킹 최고속도 조회
    @GetMapping("/total/speed")
    @Cacheable(value = RANK_TOTAL_BEST_SPEED, key = "0", unless = "#result == null", cacheManager = "cacheManager")
    public List<RankBestSpeedResponseDto> getRankTotalBestSpeed() {
        return rankService.getRankTotalBestSpeed();
    }

    // 코스별 랭킹 시간순(빨리 주행한 순)
    @GetMapping("/course/{courseId}")
    @Cacheable(value = RANK_COURSE_TIME, key = "#courseId", unless = "#result == null", cacheManager = "cacheManager")
    public List<RankCourseTimeResponseDto> getRankCourseTime(@PathVariable String courseId) {
        return rankService.getRankCourseTime(courseId);
    }

    // 개인 랭킹 시간별(+ top3)
    @GetMapping("/member/time")
    @Cacheable(value = RANK_MEMBER_TIME, key = "#member.id", unless = "#result == null", cacheManager = "cacheManager")
    public RankMemberTimeResponseDto getRankMemberTimeWithTop3(@ApiIgnore @LoginMember Member member) {
        return rankService.getRankMemberTimeWithTop3(member.getId());
    }

    // 개인 랭킹 거리별(+ top3)
    @GetMapping("/member/distance")
    @Cacheable(value = RANK_MEMBER_DISTANCE, key = "#member.id", unless = "#result == null", cacheManager = "cacheManager")
    public RankMemberDistanceResponseDto getRankMemberDistanceWithTop3(@ApiIgnore @LoginMember Member member) {
        return rankService.getRankMemberDistanceWithTop3(member.getId());
    }

    // 개인 랭킹 속도별(+ top3)
    @GetMapping("/member/speed")
    @Cacheable(value = RANK_MEMBER_BEST_SPEED, key = "#member.id", unless = "#result == null", cacheManager = "cacheManager")
    public RankMemberBestSpeedResponseDto getRankMemberSpeedwithTop3(@ApiIgnore @LoginMember Member member) {
        return rankService.getRankMemberSpeedWithTop3(member.getId());
    }
}
