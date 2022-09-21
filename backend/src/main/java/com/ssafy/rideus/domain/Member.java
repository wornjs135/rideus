package com.ssafy.rideus.domain;

import com.ssafy.rideus.domain.base.BaseEntity;
import com.ssafy.rideus.domain.type.AuthProvider;
import com.ssafy.rideus.domain.type.MemberRole;
import com.ssafy.rideus.dto.member.request.MemberMoreInfoReq;
import com.ssafy.rideus.dto.member.request.MemberUpdateRequest;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "kakao_id")
    private Long kakaoId;

    @Column(length = 20)
    private String name;

    @Column(unique = true, length = 10)
    private String nickname;

    @Column(length = 13)
    private String phone;

    @Column(length = 100)
    private String email;

    @Column(length = 200)
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    private String refreshToken;

    private Double totalDistance;
    
    private String totalTime;

    public void updateMoreInfo(MemberMoreInfoReq memberMoreInfoReq) {
        this.name = memberMoreInfoReq.getName();
        this.phone = memberMoreInfoReq.getPhone();
        this.nickname = memberMoreInfoReq.getNickname();
    }

    public void updateMember(MemberUpdateRequest request) {
        this.nickname = request.getNickname();
        this.phone = request.getTel();
        this.email = request.getEmail();
    }

    public void updateRecord(Double distance, Double time) {
        this.totalDistance += distance;
        this.totalTime = String.valueOf(Double.parseDouble(this.totalTime) + time);
    }
}
