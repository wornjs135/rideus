package com.ssafy.rideus.controller;

import com.ssafy.rideus.dto.rank.response.RankDistanceResponseDto;
import com.ssafy.rideus.dto.rank.response.RankTimeResponseDto;
import com.ssafy.rideus.service.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ssafy.rideus.config.data.CacheKey.RANK_TOTAL_DISTANCE;
import static com.ssafy.rideus.config.data.CacheKey.RANK_TOTAL_TIME;

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
}
