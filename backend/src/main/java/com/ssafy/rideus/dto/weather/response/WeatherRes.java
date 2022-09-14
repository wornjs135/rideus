package com.ssafy.rideus.dto.weather.response;

import com.ssafy.rideus.dto.weather.WeatherDto;
import lombok.*;

import java.util.List;

@ToString
@Getter
@AllArgsConstructor
@Builder
public class WeatherRes {

    private List<WeatherDto> weatherListPerHour;

}
