package com.ssafy.rideus.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewStarAvgDto {
	
    private String courseId;
    private Double count;
    private Double sum;
    
    public static ReviewStarAvgDto from(ReviewStarAvgDtoInterface list) {
    	ReviewStarAvgDto reviewStarAvgDto = new ReviewStarAvgDto();
    	reviewStarAvgDto.courseId = list.getCourseId();
    	reviewStarAvgDto.count = list.getCount();
    	reviewStarAvgDto.sum = list.getSum();

        return reviewStarAvgDto;
    }

}
