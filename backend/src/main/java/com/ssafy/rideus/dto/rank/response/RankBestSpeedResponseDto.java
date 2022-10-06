package com.ssafy.rideus.dto.rank.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankBestSpeedResponseDto implements Serializable {

    private Long memberId;
    private String nickname;
    private String profileImageUrl;
    private Double speedBest;
    private Long ranking;

    public static RankBestSpeedResponseDto from(RankBestSpeedResponseDtoInterface list) {
        RankBestSpeedResponseDto rankBestSpeedResponseDto = new RankBestSpeedResponseDto();
        rankBestSpeedResponseDto.memberId = list.getMemberId();
        rankBestSpeedResponseDto.nickname = list.getNickname();
        rankBestSpeedResponseDto.profileImageUrl = list.getProfileImageUrl();
        rankBestSpeedResponseDto.speedBest = list.getSpeedBest();
        rankBestSpeedResponseDto.ranking = list.getRanking();

        return rankBestSpeedResponseDto;
    }
}
