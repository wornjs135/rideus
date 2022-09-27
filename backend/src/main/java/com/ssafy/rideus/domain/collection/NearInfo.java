package com.ssafy.rideus.domain.collection;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "near_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class NearInfo {

    @Id
    private String id;
    private String nearinfoName;
    private String nearinfoCategory;
    private String nearinfoURL;
    private String nearinfoAddress;
    private String nearinfoTel;
    private String nearinfoLat;
    private String nearinfoLng;
}
