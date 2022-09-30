package com.ssafy.rideus.dto.coursecoordinate;

import com.ssafy.rideus.domain.base.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseCoordinateDto {

    private String id;
    private List<Coordinate> coordinates;
    private List<Coordinate> checkpoints;
    
}
