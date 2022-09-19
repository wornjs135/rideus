package com.ssafy.rideus.dto.rank.response;

import java.io.Serializable;

public interface RankTimeResponseDtoInterface extends Serializable{

    Long getMemberId();
    String getNickname();
    String getProfileImageUrl();
    String getTotalTime();
    Long getRanking();

}
