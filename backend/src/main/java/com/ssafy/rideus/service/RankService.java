package com.ssafy.rideus.service;

import com.ssafy.rideus.domain.Member;
import com.ssafy.rideus.dto.rank.response.*;
import com.ssafy.rideus.repository.jpa.MemberRepository;
import com.ssafy.rideus.repository.jpa.RecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RankService {

    private final MemberRepository memberRepository;
    private final RecordRepository recordRepository;

    public List<RankTimeResponseDto> getRankTotalTime() {
        List<RankTimeResponseDtoInterface> rankTimeResponseDtoInterfaces = memberRepository.searchRankTotalTime();

        return rankTimeResponseDtoInterfaces
                .stream()
                .map(rankTimeResponseDtoInterface -> RankTimeResponseDto.from(rankTimeResponseDtoInterface))
                .collect(Collectors.toList());
    }

    public List<RankDistanceResponseDto> getRankTotalDistance() {
        List<RankDistanceResponseDtoInterface> rankDistanceResponseDtoInterfaces = memberRepository.searchRankTotalDistance();

        return rankDistanceResponseDtoInterfaces
                .stream()
                .map(rankDistanceResponseDtoInterface -> RankDistanceResponseDto.from(rankDistanceResponseDtoInterface))
                .collect(Collectors.toList());
    }

    public List<RankBestSpeedResponseDto> getRankTotalBestSpeed() {
        List<RankBestSpeedResponseDtoInterface> rankBestSpeedResponseDtoInterfaces = recordRepository.searchRankTotalBestSpeed();

        return rankBestSpeedResponseDtoInterfaces
                .stream()
                .map(rankBestSpeedResponseDtoInterface -> RankBestSpeedResponseDto.from(rankBestSpeedResponseDtoInterface))
                .collect(Collectors.toList());
    }

    public List<RankCourseTimeResponseDto> getRankCourseTime(String courseId) {
        List<RankCourseTimeResponseDtoInterface> rankCourseTimeResponseDtos = recordRepository.searchRankCourseTime(courseId);

        return rankCourseTimeResponseDtos
                .stream()
                .map(rankCourseTimeResponseDtoInterface -> RankCourseTimeResponseDto.from(rankCourseTimeResponseDtoInterface))
                .collect(Collectors.toList());
    }

    public RankMemberTimeResponseDto getRankMemberTimeWithTop3(Long memberId) {
        List<RankTimeResponseDtoInterface> totalTimeTop3 = memberRepository.searchRankTotalTimeTop3();

        Long myTimeRank = memberRepository.searchMyRankTotalTime(memberId);
        List<RankTimeResponseDtoInterface> myTimeRankWithUpAndDown = memberRepository.searchRankMemberTimeWithUpAndDown(myTimeRank);

        return RankMemberTimeResponseDto.from(totalTimeTop3, myTimeRankWithUpAndDown);
    }

    public RankMemberDistanceResponseDto getRankMemberDistanceWithTop3(Long memberId) {
        List<RankDistanceResponseDtoInterface> totalDistanceTop3 = memberRepository.searchRankTotalDistanceTop3();

        Long myDistanceRank = memberRepository.searchMyRankTotalDistance(memberId);
        List<RankDistanceResponseDtoInterface> myDistanceRankWithUpAndDown = memberRepository.searchRankMemberDistanceWithUpAndDown(myDistanceRank);

        return RankMemberDistanceResponseDto.from(totalDistanceTop3, myDistanceRankWithUpAndDown);
    }

    public RankMemberBestSpeedResponseDto getRankMemberSpeedWithTop3(Long memberId) {
        List<RankBestSpeedResponseDtoInterface> totalBestSpeedTop3 = recordRepository.searchRankTotalBestSpeedTop3();

        Long myBestSpeedRank = recordRepository.searchMyRankTotalBestSpeed(memberId);
        List<RankBestSpeedResponseDtoInterface> myBestSpeedRankWithUpAndDown = recordRepository.searchRankMemberBestSpeedWithUpAndDown(myBestSpeedRank);

        return RankMemberBestSpeedResponseDto.from(totalBestSpeedTop3, myBestSpeedRankWithUpAndDown);
    }
}
