package com.ssafy.rideus.domain.collection;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "near_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class NearInfo {

    private String id;
    private String nearinfoName;
    private String nearinfoCategory;
    private String nearinfoURL;
    private String nearinfoAddress;
    private String nearinfoTel;
    private String nearinfoLat;
    private String nearinfoLng;
}
