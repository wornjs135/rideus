package com.ssafy.rideus.service;

import com.ssafy.rideus.common.exception.NotFoundException;
import com.ssafy.rideus.domain.base.Coordinate;
import com.ssafy.rideus.domain.collection.CourseCoordinate;
import com.ssafy.rideus.domain.collection.NearInfo;
import com.ssafy.rideus.repository.mongo.CourseCoordinateRepository;
import com.ssafy.rideus.repository.mongo.NearInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.Near;
import org.springframework.stereotype.Service;


import java.util.*;


@Service("nearInfoService")
@Slf4j
public class NearInfoServiceImpl implements NearInfoService {

    ///////////////// mongoDB Repository ////////////////
    @Autowired
    CourseCoordinateRepository courseCoordinateRepository;
    @Autowired
    NearInfoRepository nearInfoRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    static final int DISTANCE_LIMIT = 4000; // 반경 4km 안에 있는 시설 정보 조회


    @Override
    public List<NearInfo> findNearInfo(String courseId) {

        // 코스 정보 mongoDB find
        CourseCoordinate courseCoordinate =
                courseCoordinateRepository
                        .findById(courseId)
                        .orElseThrow(() -> new NotFoundException("코스 상세 조회 실패"));
        return null;
    }

    // 코스 주변 전체 정보 조회
    @Override
    public List<NearInfo> saveNearInfo(String courseId) {
        log.info("savenearinfo 시작");

        // 코스 정보 mongoDB find
        CourseCoordinate courseCoordinate =
                courseCoordinateRepository
                .findById(courseId)
                .orElseThrow(() -> new NotFoundException("코스 상세 조회 실패"));

        log.info("코스 상세 조회"+ courseCoordinate.toString());
        // mongoDB에서 체크포인트 리스트 가져오기
        List<Coordinate> checkpoints = courseCoordinate.getCheckpoints();
        log.info("get checkpoints");
        // 전체 주변 정보 리스트
        List<NearInfo> allNearInfo = nearInfoRepository.findAll();
//        Optional<NearInfo> nnn = nearInfoRepository.findById("632fe7ee6ff3393a8e8cc584");
//        log.info("nnn"+nnn.get());
        log.info("find all"+allNearInfo);
        // 주변 정보 중복 체크 map
        Map<String, NearInfo> checkedInfo = new HashMap<>();


        log.info("all Near Info size = " + allNearInfo.size());
        log.info("check point size = " + checkpoints.size());

        // 체크포인트 별로 주변정보 검색
        int checkpointCounter = 1;
        for ( Coordinate checkPoint : checkpoints ) {

            log.info(checkpointCounter++ +" "+ checkPoint);
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

        List<NearInfo> nearInfos = new ArrayList<>(checkedInfo.values());
        log.info("near Infos size = " + nearInfos.size());

//        courseCoordinateRepository.save( new CourseCoordinate(
//                courseCoordinate.getId(),
//                courseCoordinate.getCoordinates(),
//                courseCoordinate.getCheckpoints(),
//                nearInfos));
//        courseCoordinateRepository.updateNearInfo(nearInfos, courseId);
        Query query = new Query().addCriteria(Criteria.where("_id").is(courseId));
        Update update = new Update();
        update.set("nearInfos", nearInfos);
        mongoTemplate.updateFirst(query, update, CourseCoordinate.class);

        return nearInfos;
    }

    @Override
    public List<NearInfo> findAllNearInfo() {
        log.info("find all near info");
        return nearInfoRepository.findAll();
    }

    @Override
    public List<NearInfo> findNearinfoByCategories(String courseid, List<String> categories) {

        List<NearInfo> possibleNearinfos = new ArrayList<>();
        List<NearInfo> selectedInfo = new ArrayList<>();
        Map<String, Boolean> categoryMap = new HashMap<>();

        List<NearInfo> courseNearinfo = courseCoordinateRepository.findById(courseid).get().getNearInfos();

        categories.forEach(category -> categoryMap.put(category, true));

        courseNearinfo.stream().forEach(info -> {
            if(categoryMap.containsKey(info.getNearinfoCategory()))
                possibleNearinfos.add(info);
        });
        return possibleNearinfos;
    }

    @Override
    public List<NearInfo> findNearinfoByCourseid(String courseid) {
        return courseCoordinateRepository.findById(courseid).get().getNearInfos();
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
