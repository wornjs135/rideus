package com.ssafy.rideus.dto.tag.response;

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
}
