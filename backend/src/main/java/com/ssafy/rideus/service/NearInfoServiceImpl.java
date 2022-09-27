package com.ssafy.rideus.service;

import com.ssafy.rideus.common.exception.NotFoundException;
import com.ssafy.rideus.domain.base.Coordinate;
import com.ssafy.rideus.domain.collection.CourseCoordinate;
import com.ssafy.rideus.domain.collection.NearInfo;
import com.ssafy.rideus.repository.mongo.CourseCoordinateRepository;
import com.ssafy.rideus.repository.mongo.NearInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("nearInfoService")
public class NearInfoServiceImpl implements NearInfoService {

    ///////////////// mongoDB Repository ////////////////
    @Autowired
    CourseCoordinateRepository courseCoordinateRepository;
    @Autowired
    NearInfoRepository nearInfoRepository;
    static final int DISTANCE_LIMIT = 4000; // 반경 4km 안에 있는 시설 정보 조회


    @Override
    public List<NearInfo> findNearInfo(long courseId) {

        // 코스 정보 mongoDB find
        CourseCoordinate courseCoordinate =
                courseCoordinateRepository
                        .findById(String.valueOf(courseId))
                        .orElseThrow(() -> new NotFoundException("코스 상세 조회 실패"));
        return courseCoordinate.getNearInfos();
    }

    // 코스 주변 전체 정보 조회
    @Override
    public List<NearInfo> saveNearInfo(long courseId) {


        // 코스 정보 mongoDB find
        CourseCoordinate courseCoordinate =
                courseCoordinateRepository
                .findById(String.valueOf(courseId))
                .orElseThrow(() -> new NotFoundException("코스 상세 조회 실패"));


        // mongoDB에서 체크포인트 리스트 가져오기
        List<Coordinate> checkpoints = courseCoordinate.getCheckpoints();

        // 전체 주변 정보 리스트
        List<NearInfo> allNearInfo = nearInfoRepository.findAll();

        // 주변 정보 중복 체크 map
        Map<Long, NearInfo> checkedInfo = new HashMap<>();


        // 체크포인트 별로 주변정보 검색
        for ( Coordinate checkPoint : checkpoints ) {

            // 체크포인트 좌표
            double cpLat = Double.parseDouble(checkPoint.getLat());
            double cpLng = Double.parseDouble(checkPoint.getLng());

            // 주변 정보 전체 조회
            for ( NearInfo nearInfo : allNearInfo ) {

                if(checkedInfo.containsKey(nearInfo.getId())) continue;

                // 주변 정보 위,경도 좌표
                double infoLat = Double.parseDouble(nearInfo.getNearinfoLat());
                double infoLng = Double.parseDouble(nearInfo.getNearinfoLng());

                // 제한 거리 안에 존재하는 경우
                if(distance(infoLat, infoLng, cpLat, cpLng) < DISTANCE_LIMIT) {
                    checkedInfo.put(nearInfo.getId(), nearInfo);
                }

            } // end of neainfo loop
        } // end of checkpoint loop


        /*
        하둡에서 처리해야할 사항

        코스별로 mongoDB에 있는 주변정보를 가져와서
        카테고리별로 count돌리고
        가장 많은 카테고리를 MySQL에 저장!!




         */

        courseCoordinateRepository.save( new CourseCoordinate(
                courseCoordinate.getId(),
                courseCoordinate.getCoordinates(),
                courseCoordinate.getCheckpoints(),
                (List<NearInfo>) checkedInfo.values()));

        return (List<NearInfo>) checkedInfo.values();
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
