package com.ssafy.rideus.dto.member.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberMoreInfoReq {
    private String name;
    private String phone;
    private String nickname;
}
