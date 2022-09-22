package com.ssafy.rideus.dto.rank.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankMemberDistanceResponseDto implements Serializable {

    private List<RankDistanceResponseDto> top3 = new ArrayList<>();
    private List<RankDistanceResponseDto> myDistanceRankWithUpAndDown = new ArrayList<>();

    public static RankMemberDistanceResponseDto from(List<RankDistanceResponseDtoInterface> top3, List<RankDistanceResponseDtoInterface> myDistanceRankWithUpAndDown) {
        RankMemberDistanceResponseDto rankMemberDistanceResponseDto = new RankMemberDistanceResponseDto();
        rankMemberDistanceResponseDto.top3 = top3
                .stream()
                .map(rankDistanceResponseDtoInterface -> RankDistanceResponseDto.from(rankDistanceResponseDtoInterface))
                .collect(Collectors.toList());

        rankMemberDistanceResponseDto.myDistanceRankWithUpAndDown = myDistanceRankWithUpAndDown
                .stream()
                .map(rankDistanceResponseDtoInterface -> RankDistanceResponseDto.from(rankDistanceResponseDtoInterface))
                .collect(Collectors.toList());

        return rankMemberDistanceResponseDto;
    }
}
