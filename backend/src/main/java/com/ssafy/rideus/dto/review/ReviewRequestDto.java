package com.ssafy.rideus.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequestDto {
    private String recordId;
    private int score;
    private String content;
    private List<ReviewTagRequest> tags;

}
