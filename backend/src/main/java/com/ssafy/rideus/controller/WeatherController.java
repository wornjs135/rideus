package com.ssafy.rideus.controller;

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
import java.time.LocalTime;
import java.util.*;


/**
 * 날씨 API의 base_time을 지정할 때 현재 시간기준으로 입력을 하면 안된다.
 * 제공되는 API는 3시간 단위로(ex. 0, 3, 6, 9 ...) base-time을 지정할 수 있음
 * 따라서 04:12에 API요청을 하면 03:00로 요청을 보내야함.
 * 현재 시간대 부터 정보를 보여줘야 하기 때문에 item의 fcstTime을 확인해서 현재 시간 이후부터 데이터 저장
 * 이 과정에서 자정을 넘어가는 부분 체크
 *
 */
@RequestMapping("/weather")
@RequiredArgsConstructor
@RestController
public class WeatherController {



    private static final String API_KEY = "fwh2SqF7jcv3y1DAhK0KT7CBDuM5stvTsyb58Ro%2Fnbce3gHu2%2BWQlNcLnty7XiHJRzcWvdN57%2FmU3baP3O%2FZVA%3D%3D";
    private static final int PAGE_NO = 1; // 한페이지 마다 1시간 단위로 잘라서 확인
    private static final int HOURS = 12;
    private static final int UNIT_HOUR = 12;
    private static final int NUM_OF_ROW = UNIT_HOUR * HOURS; // 예보 타입 개수에 따라 12개

    // 최근 1일간의 자료만 보여줌
    @GetMapping("/today")
    public ResponseEntity<?> weatherAPI(@RequestParam String x, @RequestParam String y) throws Exception {


        /*
        API를 사용할 때 base time 으로 현재시간을 입력하면 데이터 획득 불가.
        현재 시각이 14:23이면


         */
        String date = LocalDate.now().toString().replaceAll("-", ""); /* 현재 날짜 파싱*/
        String time = offsetTime(LocalTime.now().toString().replaceAll(":", "").substring(0, 4)); /* 현재 시각 파싱, 시,분 데이터만 존재 */

        System.out.println("date = " + date);
        System.out.println("time = " + time);
        time = "1400";

        /* Open API URL 생성 */
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + API_KEY); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(PAGE_NO+"", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(NUM_OF_ROW+"", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")); /* 현재 날짜 */
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(time, "UTF-8")); /* 현재 시각 */
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(x, "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(y, "UTF-8")); /*예보지점의 Y 좌표값*/
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
        System.out.println(sb.toString());
        String result = sb.toString();


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
        String temperature="", weather="", rain="", announce="";

        for(int i=0;i<jsonArray.length();i++){
            jsonObj_4 = jsonArray.getJSONObject(i);
            String fcstValue = jsonObj_4.getString("fcstValue");
            String category = jsonObj_4.getString("category");

            // 날씨
            if(category.equals("SKY")){
                weather = fcstValue;
                announce = "현재 날씨는 ";
                if(fcstValue.equals("1")) {
                    announce += "맑은 상태로";
                }else if(fcstValue.equals("2")) {
                    announce += "비가 오는 상태로 ";
                }else if(fcstValue.equals("3")) {
                    announce += "구름이 많은 상태로 ";
                }else if(fcstValue.equals("4")) {
                    announce += "흐린 상태로 ";
                }
            }

            // 강수 확률
            if(category.equals("POP")) {
                rain = fcstValue;
            }

            // 온도
            if(category.equals("T3H") || category.equals("T1H")|| category.equals("TMP")){
                temperature = fcstValue;
                announce += "기온은 "+fcstValue+"℃ 입니다.";
            }
            if( i!=0 && i % UNIT_HOUR == 0 ) {
                weatherDtoList.add(new WeatherDto(temperature, weather, rain, announce));
                System.out.println(weatherDtoList.get(weatherDtoList.size()-1).toString());
            }
//            System.out.println(announce);
        }
        WeatherRes weatherRes = new WeatherRes(weatherDtoList);

        return new ResponseEntity<>(weatherRes, HttpStatus.OK);
    }

    private String offsetTime(String time) {
        time = time.substring(0,2) + "00";
        int t = Integer.parseInt(time);
        return (t + 2300 ) % 2400+"";
    }

}
