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
public class RankMemberBestSpeedResponseDto implements Serializable {

    private List<RankBestSpeedResponseDto> top3 = new ArrayList<>();
    private List<RankBestSpeedResponseDto> myBestSpeedRankWithUpAndDown = new ArrayList<>();


    public static RankMemberBestSpeedResponseDto from(List<RankBestSpeedResponseDtoInterface> top3, List<RankBestSpeedResponseDtoInterface> myBestSpeedRankWithUpAndDown) {
        RankMemberBestSpeedResponseDto rankMemberBestSpeedResponseDto = new RankMemberBestSpeedResponseDto();
        rankMemberBestSpeedResponseDto.top3 = top3
                .stream()
                .map(rankBestSpeedResponseDtoInterface -> RankBestSpeedResponseDto.from(rankBestSpeedResponseDtoInterface))
                .collect(Collectors.toList());

        rankMemberBestSpeedResponseDto.myBestSpeedRankWithUpAndDown = myBestSpeedRankWithUpAndDown
                .stream()
                .map(rankBestSpeedResponseDtoInterface -> RankBestSpeedResponseDto.from(rankBestSpeedResponseDtoInterface))
                .collect(Collectors.toList());

        return rankMemberBestSpeedResponseDto;
    }
}
