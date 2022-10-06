package com.ssafy.rideus.dto.coursecoordinate;

import com.ssafy.rideus.domain.base.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseCheckpointDto {

    private String id;
    private List<Coordinate> checkpoints;
    
}
