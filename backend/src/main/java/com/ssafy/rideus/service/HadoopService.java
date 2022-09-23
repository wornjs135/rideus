package com.ssafy.rideus.service;


public interface HadoopService {

    // output파일 local로 가져와서 mysql에 save
    void saveCategoryToCourse(String courseid);

}
