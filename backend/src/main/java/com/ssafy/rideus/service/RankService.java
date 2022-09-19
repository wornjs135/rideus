package com.ssafy.rideus.service;

import com.ssafy.rideus.dto.rank.response.RankDistanceResponseDto;
import com.ssafy.rideus.dto.rank.response.RankDistanceResponseDtoInterface;
import com.ssafy.rideus.dto.rank.response.RankTimeResponseDto;
import com.ssafy.rideus.dto.rank.response.RankTimeResponseDtoInterface;
import com.ssafy.rideus.repository.jpa.MemberRepository;
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
}
