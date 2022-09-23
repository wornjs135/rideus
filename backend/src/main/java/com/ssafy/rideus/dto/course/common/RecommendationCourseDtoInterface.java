package com.ssafy.rideus.dto.course.common;

import java.util.List;

public interface RecommendationCourseDtoInterface {

    String getCourseId();
    String getCourseName();
    String getDistance();
    String getExpectedTime();
    String getStart();
    String getFinish();
    Integer getLikeCount();
    Long getTagId();
    String getTagName();
    Integer getMax();

}
