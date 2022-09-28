package com.ssafy.rideus.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewTagRequest {

    private Long id;
    private String searchTagName;
}
