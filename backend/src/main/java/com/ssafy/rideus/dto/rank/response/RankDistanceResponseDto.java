package com.ssafy.rideus.dto.rank.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankDistanceResponseDto implements Serializable {

    private Long memberId;
    private String nickname;
    private String profileImageUrl;
    private Double totalDistance;
    private Long ranking;

    public static RankDistanceResponseDto from(RankDistanceResponseDtoInterface list) {
        RankDistanceResponseDto rankDistanceResponseDto = new RankDistanceResponseDto();
        rankDistanceResponseDto.memberId = list.getMemberId();
        rankDistanceResponseDto.nickname = list.getNickname();
        rankDistanceResponseDto.profileImageUrl = list.getProfileImageUrl();
        rankDistanceResponseDto.totalDistance = list.getTotalDistance();
        rankDistanceResponseDto.ranking = list.getRanking();

        return rankDistanceResponseDto;
    }
}
