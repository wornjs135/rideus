package com.ssafy.rideus.common.api;


import com.ssafy.rideus.domain.base.Coordinate;
import com.ssafy.rideus.service.CourseService;

public class CheckFinish {

    public static boolean checkFinished(Coordinate myFinishCoordinate, Coordinate courseFinishCoordinate) {
        System.out.println("여기여기?1");
        boolean result;
        System.out.println("여기여기?2");

        double lat1 = Double.parseDouble(myFinishCoordinate.getLat());
        System.out.println("여기여기?3");

        double lng1 = Double.parseDouble(myFinishCoordinate.getLng());
        System.out.println("여기여기?4");

        double lat2 = Double.parseDouble(courseFinishCoordinate.getLat());
        System.out.println("여기여기?5");

        double lng2 = Double.parseDouble(courseFinishCoordinate.getLng());
        System.out.println("여기여기?6");


        double distance = CourseService.intervalMeter(lat1, lng1, lat2, lng2);
        System.out.println("여기여기?7");


        if (distance <= 500) {
            result = true;
        } else {
            result = false;
        }
        System.out.println("설마여기?" + result);

        return result;
    }
}
