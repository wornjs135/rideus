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
public class RankMemberTimeResponseDto implements Serializable {

    private List<RankTimeResponseDto> top3 = new ArrayList<>();
    private List<RankTimeResponseDto> myTimeRankWithUpAndDown = new ArrayList<>();

    public static RankMemberTimeResponseDto from(List<RankTimeResponseDtoInterface> top3, List<RankTimeResponseDtoInterface> myTimeRankWithUpAndDown) {
        RankMemberTimeResponseDto rankMemberTimeResponseDto = new RankMemberTimeResponseDto();
        rankMemberTimeResponseDto.top3 = top3
                .stream()
                .map(rankTimeResponseDtoInterface -> RankTimeResponseDto.from(rankTimeResponseDtoInterface))
                .collect(Collectors.toList());

        rankMemberTimeResponseDto.myTimeRankWithUpAndDown = myTimeRankWithUpAndDown
                .stream()
                .map(rankTimeResponseDtoInterface -> RankTimeResponseDto.from(rankTimeResponseDtoInterface))
                .collect(Collectors.toList());

        return rankMemberTimeResponseDto;
    }
}
