package com.ssafy.rideus.dto.course.common;

import java.util.List;

public interface RecommendationCourseDtoInterface {

    String getCourseId();
    String getCourseName();
    Double getDistance();
    Integer getExpectedTime();
    String getStart();
    String getFinish();
    Integer getLikeCount();
    String getImageUrl();
    String getCategory();
    Long getTagId();
    String getTagName();
    Integer getMax();

}
