package com.ssafy.rideus.dto.rideroom.common;

import com.ssafy.rideus.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDto implements Serializable {

    private Long memberId;
    private String nickname;
    private String color;
    private String profileImageUrl;

    public static ParticipantDto from(Member member) {
        ParticipantDto participant = new ParticipantDto();
        participant.memberId = member.getId();
        participant.nickname = member.getNickname();
        participant.profileImageUrl = member.getProfileImageUrl();

        return participant;
    }
}
