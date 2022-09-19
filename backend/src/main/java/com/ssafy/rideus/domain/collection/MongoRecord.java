package com.ssafy.rideus.domain.collection;

import com.ssafy.rideus.domain.base.Coordinate;
import com.ssafy.rideus.dto.rideroom.common.ParticipantDto;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "record")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class MongoRecord {

    @Id
    private String id;

    private Long memberId;

    private List<Coordinate> coordinates = new ArrayList<>();

    private List<ParticipantDto> participants = new ArrayList<>();

    public static MongoRecord create(Long memberId) {
        MongoRecord record = new MongoRecord();
        record.memberId = memberId;

        return record;
    }

    public void updateParticipants(List<ParticipantDto> participants) {
        this.participants = participants;
    }
}
