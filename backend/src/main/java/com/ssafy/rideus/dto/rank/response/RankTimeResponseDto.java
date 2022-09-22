package com.ssafy.rideus.dto.rank.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankTimeResponseDto implements Serializable {

    private Long memberId;
    private String nickname;
    private String profileImageUrl;
    private String totalTime;
    private Long ranking;

    public static RankTimeResponseDto from(RankTimeResponseDtoInterface list) {
        RankTimeResponseDto rankTimeResponseDto = new RankTimeResponseDto();
        rankTimeResponseDto.memberId = list.getMemberId();
        rankTimeResponseDto.nickname = list.getNickname();
        rankTimeResponseDto.profileImageUrl = list.getProfileImageUrl();
        rankTimeResponseDto.totalTime = list.getTotalTime();
        rankTimeResponseDto.ranking = list.getRanking();

        return rankTimeResponseDto;
    }
}
