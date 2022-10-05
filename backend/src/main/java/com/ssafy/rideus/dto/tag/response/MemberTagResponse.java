package com.ssafy.rideus.dto.tag.response;

import com.ssafy.rideus.domain.MemberTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberTagResponse {
    private String tagName;
    private Long id;
    private Long tagCount;

    public static MemberTagResponse from(MemberTag memberTag) {
        MemberTagResponse memberTagResponse = new MemberTagResponse();
        memberTagResponse.tagName = memberTag.getTag().getTagName();
        memberTagResponse.id = memberTag.getTag().getId();

        return memberTagResponse;
    }
}
