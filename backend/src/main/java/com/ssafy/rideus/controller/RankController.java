package com.ssafy.rideus.controller;

import com.ssafy.rideus.config.security.auth.CustomUserDetails;
import com.ssafy.rideus.config.web.LoginMember;
import com.ssafy.rideus.domain.Member;
import com.ssafy.rideus.dto.rank.response.*;
import com.ssafy.rideus.service.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
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
//    @Cacheable(value = RANK_COURSE_TIME, key = "#courseId", unless = "#result == null", cacheManager = "cacheManager")
    public List<RankCourseTimeResponseDto> getRankCourseTime(@PathVariable String courseId) {
        return rankService.getRankCourseTime(courseId);
    }

    // 개인 랭킹 시간별(+ top3)
    @GetMapping("/member/time")
    @Cacheable(value = RANK_MEMBER_TIME, key = "#member.id", unless = "#result == null", cacheManager = "cacheManager")
    public RankMemberTimeResponseDto getRankMemberTimeWithTop3(@ApiIgnore @AuthenticationPrincipal CustomUserDetails member) {
        return rankService.getRankMemberTimeWithTop3(member.getId());
    }

    // 개인 랭킹 거리별(+ top3)
    @GetMapping("/member/distance")
    @Cacheable(value = RANK_MEMBER_DISTANCE, key = "#member.id", unless = "#result == null", cacheManager = "cacheManager")
    public RankMemberDistanceResponseDto getRankMemberDistanceWithTop3(@ApiIgnore @AuthenticationPrincipal CustomUserDetails member) {
        return rankService.getRankMemberDistanceWithTop3(member.getId());
    }

    // 개인 랭킹 속도별(+ top3)
    @GetMapping("/member/speed")
    @Cacheable(value = RANK_MEMBER_BEST_SPEED, key = "#member.id", unless = "#result == null", cacheManager = "cacheManager")
    public RankMemberBestSpeedResponseDto getRankMemberSpeedwithTop3(@ApiIgnore @AuthenticationPrincipal CustomUserDetails member) {
        return rankService.getRankMemberSpeedWithTop3(member.getId());
    }

    @DeleteMapping("/total/time")
    @CacheEvict(value = RANK_TOTAL_TIME, key = "0")
    public ResponseEntity deleteCacheRankTotalTime() {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/total/distance")
    @CacheEvict(value = RANK_TOTAL_DISTANCE, key = "0")
    public ResponseEntity deleteCacheTotalDistance() {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/total/speed")
    @CacheEvict(value = RANK_TOTAL_BEST_SPEED, key = "0")
    public ResponseEntity deleteCacheTotalBestSpeed() {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/course")
    @CacheEvict(value = RANK_COURSE_TIME, allEntries = true)
    public ResponseEntity deleteCacheCourseTime() {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/member/time")
    @CacheEvict(value = RANK_MEMBER_TIME, allEntries = true)
    public ResponseEntity deleteCacheMemberTimeWithTop3() {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/member/distance")
    @CacheEvict(value = RANK_MEMBER_DISTANCE, allEntries = true)
    public ResponseEntity deleteCacheMemberDistanceWithTop3() {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/member/speed")
    @CacheEvict(value = RANK_MEMBER_BEST_SPEED, allEntries = true)
    public ResponseEntity deleteCacheMemberSpeedwithTop3() {
        return ResponseEntity.ok().build();
    }
}
