package com.ssafy.rideus.dto.review;

import com.ssafy.rideus.domain.Course;
import com.ssafy.rideus.domain.Member;
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
public class ReviewResponseDto {
    private Member member;
    private Course course;
    private int score;
    private String content;
    private String imageUrl;
    private List<Tag> tags;
    private int likeCount;
}
