package com.ssafy.rideus.dto.member.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequest {
    
    @ApiModelProperty(notes = "닉네임")
    private String nickname;
    @ApiModelProperty(notes = "전화번호")
    private String tel;
    @ApiModelProperty(notes = "이메일")
    private String email;
}