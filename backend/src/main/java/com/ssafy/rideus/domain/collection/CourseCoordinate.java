package com.ssafy.rideus.domain.collection;

import lombok.*;

import org.springframework.data.mongodb.core.mapping.Document;

import com.ssafy.rideus.domain.base.Coordinate;

import javax.persistence.Id;
import java.util.List;
import java.util.Map;

@Document(collection = "course_coordinate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class CourseCoordinate {

    @Id
    private String id;
    private List<Coordinate> coordinates;
    private List<Coordinate> checkpoints;
    private List<NearInfo> nearInfos;
    //관광명소, 음식점, 카페, 편의점, 문화시설, 공중화장실, 자전거정비
    private Map<String, List<String>> nearInfoIds;

    public static CourseCoordinate create(List<Coordinate> coordinates, List<Coordinate> checkpoints) {
        CourseCoordinate courseCoordinate = new CourseCoordinate();
        courseCoordinate.coordinates = coordinates;
        courseCoordinate.checkpoints = checkpoints;

        return courseCoordinate;
    }

}
