package com.ssafy.rideus.service;

import com.ssafy.rideus.domain.collection.NearInfo;

import java.util.List;

public interface NearInfoService {

    List<NearInfo> findNearInfo(String courseId);

    // 코스 주변 전체 정보 조회
    List<NearInfo> saveNearInfo(String courseId);

    List<NearInfo> findAllNearInfo();

    List<NearInfo> findNearinfoByCategories(String courseid, List<String> categories);

    List<NearInfo> findNearinfoByCourseid(String courseid);
}
