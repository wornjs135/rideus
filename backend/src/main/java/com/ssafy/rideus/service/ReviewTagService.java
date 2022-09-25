package com.ssafy.rideus.service;

import com.ssafy.rideus.domain.Tag;
import com.ssafy.rideus.dto.tag.common.TagDto;
import com.ssafy.rideus.repository.jpa.ReviewTagRepository;
import com.ssafy.rideus.repository.query.ReviewTagQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewTagService {

    private final ReviewTagQueryRepository reviewTagQueryRepository;

    public List<TagDto> getPopularityTag() {
        List<Tag> popularityTags = reviewTagQueryRepository.getPopularityTag();

        return popularityTags
                .stream()
                .map(tag -> TagDto.from(tag))
                .collect(Collectors.toList());
    }
}
