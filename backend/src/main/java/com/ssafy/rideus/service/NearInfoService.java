package com.ssafy.rideus.service;

import com.ssafy.rideus.common.exception.NotFoundException;
import com.ssafy.rideus.config.web.GlobalExceptionHandler;
import com.ssafy.rideus.domain.collection.CourseCoordinate;
import com.ssafy.rideus.domain.collection.NearInfo;
import com.ssafy.rideus.repository.mongo.CourseCoordinateRepository;
import com.ssafy.rideus.repository.mongo.NearInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;


import java.util.*;


@Service("nearInfoService")
@RequiredArgsConstructor
@Slf4j
public class NearInfoService {

    private final CourseCoordinateRepository courseCoordinateRepository;
    private final NearInfoRepository nearInfoRepository;



    public List<NearInfo> findNearInfoByCategory(String courseId, String category) {

        CourseCoordinate courseCoordinate =
                courseCoordinateRepository
                        .findById(courseId)
                        .orElseThrow(() -> new NotFoundException("코스 상세 조회 실패"));

        Map<String, List<String>> nearInfoIds = courseCoordinate.getNearInfoIds();

        if(!nearInfoIds.keySet().contains(category)) return null;

        List<String> nearInfoList = nearInfoIds.get(category);

        List<NearInfo> nearInfos = new ArrayList<>();
        nearInfoList.forEach(nearInfoId -> {
            Optional<NearInfo> nearInfo = nearInfoRepository.findById(nearInfoId);
            nearInfos.add(nearInfo.get());
        });

        return nearInfos;
    }



    //------------------------- 위 경도 두 좌표 사이의 거리 계산 --------------------------

    // dsitance(첫번쨰 좌표의 위도, 첫번째 좌표의 경도, 두번째 좌표의 위도, 두번째 좌표의 경도)
    public static double distance(double lat1, double lon1, double lat2, double lon2){
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))* Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))*Math.cos(deg2rad(lat2))*Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60*1.1515*1609.344;

        return dist; //단위 meter
    }
    //10진수를 radian(라디안)으로 변환
    private static double deg2rad(double deg){
        return (deg * Math.PI/180.0);
    }
    //radian(라디안)을 10진수로 변환
    private static double rad2deg(double rad){
        return (rad * 180 / Math.PI);
    }

}
