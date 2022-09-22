package com.ssafy.rideus.dto.review;

import com.ssafy.rideus.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDetailResponseDto {
    private Long mid;
    private String content;
    private List<String> tags;

}
