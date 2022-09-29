package com.ssafy.rideus.dto.member.response;

import com.ssafy.rideus.domain.Member;
import com.ssafy.rideus.domain.type.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberMeRes {
    private Long id;

    private String name;

    private String nickname;

    private String phone;

    private String email;

    private String profileImageUrl;

    private MemberRole role;

    public static MemberMeRes of(Member member) {
        return MemberMeRes.builder()
                .id(member.getId())
                .name(member.getName())
                .nickname(member.getNickname())
                .phone(member.getPhone())
                .email(member.getEmail())
                .profileImageUrl(member.getProfileImageUrl())
                .role(member.getRole())
                .build();
    }
}
