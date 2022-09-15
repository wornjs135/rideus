package com.ssafy.rideus.service;

import com.ssafy.rideus.domain.CheckPoint;
import com.ssafy.rideus.domain.Course;
import com.ssafy.rideus.domain.NearInfo;
import com.ssafy.rideus.repository.jpa.CheckPointRepository;
import com.ssafy.rideus.repository.jpa.CourseRepository;
import com.ssafy.rideus.repository.jpa.NearInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("nearInfoService")
public class NearInfoServiceImpl implements NearInfoService {

    @Autowired
    NearInfoRepository nearInfoRepository;
    @Autowired
    CheckPointRepository checkPointRepository;
    @Autowired
    CourseRepository courseRepository;


    static final int DISTANCE_LIMIT = 2000; // 반경 2km 안에 있는 시설 정보 조회


    // 코스 주변 전체 정보 조회
    @Override
    public List<NearInfo> findAllNearInfo(long courseId) {

        Course course = courseRepository.findById(courseId).orElse(null);
        List<CheckPoint> checkPoints = checkPointRepository.findByCourse(course);
        List<NearInfo> nearInfos = nearInfoRepository.findAll();

        // 반경 안에 있는 주변 정보 map
        // 각 체크포인트 지점에서 겹치는 시설이 있는 경우 고려해서 map 사용
        Map<Long, NearInfo> resultInfos = new HashMap<>();

        // 체크포인트 주변 정보 조회
        for( CheckPoint checkPoint : checkPoints ) {

            // 체크포인트 위,경도 좌표
            double cpLat = Double.parseDouble(checkPoint.getCheckPointLat());
            double cpLng = Double.parseDouble(checkPoint.getCheckPointLng());

            // 주변정보 리스트 조회
            for( NearInfo info : nearInfos ) {

                // 이미 찾은 주변 정보인 경우
                if(resultInfos.containsKey(info.getId())) continue;
                else {
                    // 주변 정보 위,경도 좌표
                    double infoLat = Double.parseDouble(info.getNearinfoLat());
                    double infoLng = Double.parseDouble(info.getNearinfoLng());

                    // 제한 거리 안에 존재하는 경우
                    if(distance(infoLat, infoLng, cpLat, cpLng) < DISTANCE_LIMIT) {
                        resultInfos.put(info.getId(), info);
                    }
                }
            }
        }

        return (List<NearInfo>) resultInfos.values();

    }


    //------------------------- 위 경도 두 좌표 사이의 거리 계산 --------------------------

    // dsitance(첫번쨰 좌표의 위도, 첫번째 좌표의 경도, 두번째 좌표의 위도, 두번째 좌표의 경도)
    private static double distance(double lat1, double lon1, double lat2, double lon2){
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
