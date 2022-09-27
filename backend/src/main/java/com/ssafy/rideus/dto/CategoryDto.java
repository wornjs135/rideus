package com.ssafy.rideus.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Setter
public class CategoryDto implements Comparable<CategoryDto>{

    String category;
    int count;

    @Override
    public int compareTo(CategoryDto o) {
        return o.count - this.count;
    }


}
