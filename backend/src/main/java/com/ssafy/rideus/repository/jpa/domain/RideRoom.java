package com.ssafy.rideus.repository.jpa.domain;

import com.ssafy.rideus.repository.jpa.domain.base.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class RideRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    public static RideRoom create(Member member) {
        RideRoom rideRoom = new RideRoom();
        rideRoom.member = member;

        return rideRoom;
    }
}
