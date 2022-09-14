package com.ssafy.rideus.domain;

import com.ssafy.rideus.domain.base.BaseEntity;
import com.ssafy.rideus.domain.type.AuthProvider;
import com.ssafy.rideus.domain.type.MemberRole;
import com.ssafy.rideus.dto.member.request.MemberMoreInfoReq;
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

    @Column(length = 50)
    private String name;

    @Column(unique = true)
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

    public void addMoreInfo(MemberMoreInfoReq memberMoreInfoReq) {
        this.name = memberMoreInfoReq.getName();
        this.phone = memberMoreInfoReq.getPhone();
        this.nickname = memberMoreInfoReq.getNickname();
    }
}
