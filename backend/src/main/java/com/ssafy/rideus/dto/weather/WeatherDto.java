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

    private String temperature;
    private String weather;
    private String rain;
    private String announce;

}
