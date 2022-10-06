package com.ssafy.rideus.dto.rank.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankCourseTimeResponseDto implements Serializable {

    private Long memberId;
    private String nickname;
    private String profileImageUrl;
    private Double timeMinute;
    private Long ranking;

    public static RankCourseTimeResponseDto from(RankCourseTimeResponseDtoInterface list) {
        RankCourseTimeResponseDto rankCourseTimeResponseDto = new RankCourseTimeResponseDto();
        rankCourseTimeResponseDto.memberId = list.getMemberId();
        rankCourseTimeResponseDto.nickname = list.getNickname();
        rankCourseTimeResponseDto.profileImageUrl = list.getProfileImageUrl();
        rankCourseTimeResponseDto.timeMinute = list.getTimeMinute();
        rankCourseTimeResponseDto.ranking = list.getRanking();

        return rankCourseTimeResponseDto;
    }
}
