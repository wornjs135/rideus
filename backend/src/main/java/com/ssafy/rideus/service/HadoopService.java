package com.ssafy.rideus.service;


import com.ssafy.rideus.domain.collection.NearInfo;

import java.io.File;
import java.util.List;

public interface HadoopService {

    // output파일 local로 가져와서 mysql에 save
    List<NearInfo> updateCourseNearInfo(String courseid);
    void saveCategoryToCourse(String courseid);
    void parsingCourseCategory(List<NearInfo> courseNearinfos, String courseid);

    // 생성된 파일 hdfs안에 저장
    void copyFileToHdfs(File file, String filePath, String courseid);

    void settingDB();
}
