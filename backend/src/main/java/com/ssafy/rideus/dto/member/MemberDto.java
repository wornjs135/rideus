package com.ssafy.rideus.dto.member;


import com.ssafy.rideus.domain.Member;
import com.ssafy.rideus.domain.type.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private Long id;

    private Long kakaoId;

    private String name;

    private String phone;


    private String email;


    private String profileImageUrl;


    private MemberRole role;
}
