package com.ssafy.rideus.controller;

import com.ssafy.rideus.dto.weather.GpsTransfer;
import com.ssafy.rideus.dto.weather.WeatherDto;
import com.ssafy.rideus.dto.weather.response.WeatherRes;
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

    private static final String API_KEY = "fwh2SqF7jcv3y1DAhK0KT7CBDuM5stvTsyb58Ro%2Fnbce3gHu2%2BWQlNcLnty7XiHJRzcWvdN57%2FmU3baP3O%2FZVA%3D%3D";
    private static final String PAGE_NO = "1"; // 한페이지 마다 1시간 단위로 잘라서 확인
    private static final String HOURS = "12";
    private static final String UNIT_HOUR = "12";
    private static final String NUM_OF_ROW = "144"; // 예보 타입 개수에 따라 12개
//    private static final int NUM_OF_ROW = 1000; // 예보 타입 개수에 따라 12개
    private static String baseDate, baseTime;
    // 최근 1일간의 자료만 보여줌


    @GetMapping("/gps")
    public ResponseEntity<?> weatherAPI(@RequestParam double lat, @RequestParam double lon) throws Exception {


        System.out.println("lat = " + lat);
        System.out.println("lon = " + lon);
        GpsTransfer gpsTransfer = new GpsTransfer(lat, lon);
        System.out.println("gpsTransfer.getXLat() = " + gpsTransfer.getXLat());
        System.out.println("gpsTransfer.getYLon() = " + gpsTransfer.getYLon());
        final String nx = gpsTransfer.getXLat()+"";
        final String ny = gpsTransfer.getYLon()+"";

        System.out.println("nx = " + nx);
        System.out.println("ny = " + ny);
        settingDate(); /* 날씨 데이터 세팅*/

        /* Open API URL 생성 */
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + API_KEY); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(PAGE_NO, "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(NUM_OF_ROW, "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /* 현재 날짜 */
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); /* 현재 시각 */
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); /*예보지점의 Y 좌표값*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        conn.connect();
        System.out.println("url.toString() = " + url.toString());

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        rd.close();
        conn.disconnect();
        String result = sb.toString();
        System.out.println("result = " + result);

        //=======이 밑에 부터는 json에서 데이터 파싱해 오는 부분이다=====//

        // response 키를 가지고 데이터를 파싱
        JSONObject jsonObj_1 = new JSONObject(result);
        String response = jsonObj_1.getString("response");

        // response 로 부터 body 찾기
        JSONObject jsonObj_2 = new JSONObject(response);
        String body = jsonObj_2.getString("body");

        // body 로 부터 items 찾기
        JSONObject jsonObj_3 = new JSONObject(body);
        String items = jsonObj_3.getString("items");

        // items로 부터 itemlist 를 받기
        JSONObject jsonObj_4 = new JSONObject(items);
        JSONArray jsonArray = jsonObj_4.getJSONArray("item");


        List<WeatherDto> weatherDtoList = new ArrayList<>();
        // 시간대별 저장 데이터
        String temperature="", weather="", rain="", announce="";

        for(int i=0;i<jsonArray.length();i++){
            jsonObj_4 = jsonArray.getJSONObject(i);
            String fcstValue = jsonObj_4.getString("fcstValue");
            String category = jsonObj_4.getString("category");

            // 날씨
            if(category.equals("SKY")) weather = fcstValue;
            // 강수 확률
            if(category.equals("POP")) rain = fcstValue;
            // 온도
            if(category.equals("TMP")) temperature = fcstValue;

            if( i!=0 && i % Integer.parseInt(UNIT_HOUR) == 0 ) {
                weatherDtoList.add(new WeatherDto(temperature, weather, rain));
                System.out.println(weatherDtoList.get(weatherDtoList.size()-1).toString());
            }
        }
        WeatherRes weatherRes = new WeatherRes(weatherDtoList);

        return new ResponseEntity<>(weatherRes, HttpStatus.OK);
//        return new ResponseEntity<String>(result, HttpStatus.OK);

    }

    private void settingDate() {

        LocalDateTime today = LocalDateTime.now(); /* format : 2022-09-15T13:30:05.127 */
        String todayString = today.toString().replaceAll("-|:|T", "");


        baseDate = todayString.substring(0,8);

        int hour =  Integer.parseInt(todayString.substring(8,10));
        if(hour < 2) {
            String yesterday = yesterday(today);
            baseDate = yesterday.replaceAll("-", "");
            baseTime = "2300";
        } else {
            hour = ( ( hour+1 ) / 3 ) * 3 - 1 ;
            if( hour < 10 ) baseTime = "0"+hour+"00";
            else baseTime = hour+"00";
        }
        System.out.println("baseDate = " + baseDate);
        System.out.println("baseTime = " + baseTime);
    }

    public static String yesterday(LocalDateTime now) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd");
        return now.minus(1, ChronoUnit.DAYS).withHour(0).withMinute(0)
                .withSecond(0).format(formatter);
    }/*from  w ww  .ja v a 2 s  .c  om*/

}
