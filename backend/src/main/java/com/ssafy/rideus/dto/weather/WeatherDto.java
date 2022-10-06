package com.ssafy.rideus.dto.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@Builder
public class WeatherDto {
        /*
            Weather Dto

            temperature : 현재 기온
            rain : 강수 확률
            weather : 날씨 상태
            1 : 맑은 상태
            2 : 비가 오는 상태
            3 : 구름 많은 상태
            4 : 흐린 상태

         */

    private String temperature; // 현재 온도
    private String weather;     // 날씨 상태
    private String rain;        // 강수 확률

}
