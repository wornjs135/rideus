package com.ssafy.rideus.domain.collection;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "course_near_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class NearInfo {

    private Long id;
    private String nearinfoName;
    private String nearinfoCategory;
    private String nearinfoUrl;
    private String nearinfoAddress;
    private String nearinfoTel;
    private String nearinfoLat;
    private String nearinfoLng;
}
