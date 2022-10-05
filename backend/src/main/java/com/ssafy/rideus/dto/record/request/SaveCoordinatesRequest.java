package com.ssafy.rideus.dto.record.request;

import com.ssafy.rideus.domain.base.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveCoordinatesRequest {

    private List<Coordinate> coordinates = new ArrayList<>();
    private Long rideRoomId;
}
