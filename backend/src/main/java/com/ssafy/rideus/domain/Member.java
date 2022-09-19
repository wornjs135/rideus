package com.ssafy.rideus.domain;

import com.ssafy.rideus.domain.base.BaseEntity;
import com.ssafy.rideus.domain.type.MemberRole;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "kakao_id")
    private Long kakaoId;

    @Column(length = 50)
    private String name;

    @Column(length = 13)
    private String phone;

    @Column(length = 100)
    private String email;

    @Column(length = 200)
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private MemberRole role;

    @Column(length = 100)
    private String nickname;

    private Double totalDistance;
    private String totalTime;

    public void updateRecord(Double distance, Double time) {
        this.totalDistance += distance;
        this.totalTime = String.valueOf(Double.parseDouble(this.totalTime) + time);
    }
}
