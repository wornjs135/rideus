package com.ssafy.rideus.dto.tag.common;

import com.ssafy.rideus.domain.CourseTag;
import com.ssafy.rideus.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDto implements Serializable {

    private Long tagId;
    private String tagName;

    public static TagDto from(Long tagId, String tagName) {
        TagDto tagDto = new TagDto();
        tagDto.tagId = tagId;
        tagDto.tagName = tagName;

        return tagDto;
    }

    public static TagDto from(CourseTag courseTag) {
        Tag tag = courseTag.getTag();

        TagDto tagDto = new TagDto();
        tagDto.tagId = tag.getId();
        tagDto.tagName = tag.getTagName();

        return tagDto;
    }

    public static TagDto from(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.tagId = tag.getId();
        tagDto.tagName = tag.getTagName();

        return tagDto;
    }
}
