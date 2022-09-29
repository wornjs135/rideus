package com.ssafy.rideus.controller;

import com.ssafy.rideus.dto.weather.GpsTransfer;
import com.ssafy.rideus.dto.weather.WeatherDto;
import com.ssafy.rideus.dto.weather.response.WeatherRes;
import com.ssafy.rideus.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;


/**
 * API Request BaseDate, BaseTime 세팅 정보 수정
 * 단기 예보의 경우 특정 시간대만 검색 가능 (2,5,8,11,14,17,20,23)
 * 날씨 API의 base_time을 지정할 때 현재 시간기준으로 입력을 하면 안된다.
 * 이 과정에서 자정을 넘어가는 부분 체크
 *
 */
@RequestMapping("/weather")
@RequiredArgsConstructor
@RestController
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/gps")
    public ResponseEntity<WeatherRes> weatherAPI(@RequestParam double lat, @RequestParam double lon) throws Exception {
        return new ResponseEntity<WeatherRes>(weatherService.weatherAPI(lat,lon), HttpStatus.OK);
    }


}
